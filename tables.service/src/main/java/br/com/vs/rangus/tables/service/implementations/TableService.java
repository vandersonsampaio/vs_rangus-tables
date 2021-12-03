package br.com.vs.rangus.tables.service.implementations;

import br.com.vs.rangus.tables.client.merchant.MerchantClient;
import br.com.vs.rangus.tables.enums.StatusTable;
import br.com.vs.rangus.tables.exceptions.MerchantNotFoundException;
import br.com.vs.rangus.tables.model.Occupation;
import br.com.vs.rangus.tables.model.Participant;
import br.com.vs.rangus.tables.model.Table;
import br.com.vs.rangus.tables.exceptions.TableNotFoundException;
import br.com.vs.rangus.tables.repository.IOccupationRepository;
import br.com.vs.rangus.tables.repository.IParticipantRepository;
import br.com.vs.rangus.tables.repository.ITableRepository;
import br.com.vs.rangus.tables.service.interfaces.ITableService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TableService implements ITableService {

    private final ITableRepository repository;
    private final IParticipantRepository participantRepository;
    private final IOccupationRepository occupationRepository;

    private final MerchantClient merchantClient;

    public TableService(ITableRepository repository, IParticipantRepository participantRepository,
                        IOccupationRepository occupationRepository, MerchantClient merchantClient) {
        this.repository = repository;
        this.participantRepository = participantRepository;
        this.occupationRepository = occupationRepository;
        this.merchantClient = merchantClient;
    }

    @Transactional
    @Override
    public String sit(String id, String user) throws TableNotFoundException {
        Table table = repository.findById(id).orElseThrow(() -> new TableNotFoundException(id));

        assert table.getStatus() != StatusTable.UNAVAILABLE;

        if(table.getStatus().equals(StatusTable.AVAILABLE)) {
            table.setStatus(StatusTable.BUSY);
            buildOccupation(table, user);
        } else {
            addParticipant(id, user);
        }

        return table.getMerchant();
    }

    @Override
    public List<String> findAllMerchants(String idMerchant) throws MerchantNotFoundException {
        List<String> allMerchants = merchantClient.findAll(idMerchant).getBody();

        if(allMerchants == null || allMerchants.isEmpty()) throw new MerchantNotFoundException(idMerchant);

        return allMerchants;
    }

    @Transactional
    @Override
    public boolean getUp(String idTable) throws TableNotFoundException {
        Table table = repository.findById(idTable).orElseThrow(() -> new TableNotFoundException(idTable));

        assert table.getStatus() == StatusTable.BUSY;
        table.setStatus(StatusTable.AVAILABLE);

        Occupation occupation = findOccupation(idTable);
        occupation.setFinish(LocalDateTime.now());
        occupationRepository.save(occupation);

        repository.save(table);
        return true;
    }

    private void addParticipant(String idTable, String user) {
        Occupation occupation = findOccupation(idTable);
        buildParticipant(occupation, user);
    }

    private Occupation findOccupation(String idTable) {
        List<Occupation> occupationList = occupationRepository.findAllByTableAndFinishIsNull(idTable);
        assert occupationList.size() == 1;
        return occupationList.stream().findFirst().get();
    }

    private void buildOccupation(Table table, String user) {
        Occupation occupation = Occupation.builder().table(table)
                .initial(LocalDateTime.now()).user(user).build();

        occupation = occupationRepository.save(occupation);

        occupation.getParticipants().add(buildParticipant(occupation, user));
    }

    private Participant buildParticipant(Occupation occupation, String user) {
        Participant participant = Participant.builder().dateTimeSit(LocalDateTime.now())
                .occupation(occupation).user(user).build();

        participantRepository.save(participant);

        return participant;
    }
}
