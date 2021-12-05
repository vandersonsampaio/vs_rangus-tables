package br.com.vs.rangus.tables.service.implementations;

import br.com.vs.rangus.tables.client.merchant.MerchantClient;
import br.com.vs.rangus.tables.dto.response.ResponseMerchantDTO;
import br.com.vs.rangus.tables.dto.response.ResponseTableDTO;
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
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
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
            addParticipant(table, user);
        }

        return table.getMerchant();
    }

    @Override
    public ResponseMerchantDTO findAllMerchants(String idMerchant) throws MerchantNotFoundException {
        ResponseMerchantDTO merchants = merchantClient.findAll(idMerchant).getBody();

        if(merchants == null) throw new MerchantNotFoundException(idMerchant);

        return merchants;
    }

    @Transactional
    @Override
    public ResponseTableDTO getUp(String idTable) throws TableNotFoundException {
        Table table = repository.findById(idTable).orElseThrow(() -> new TableNotFoundException(idTable));

        assert table.getStatus() == StatusTable.BUSY;
        table.setStatus(StatusTable.AVAILABLE);

        Occupation occupation = findOccupation(table);
        occupation.setFinish(LocalDateTime.now());
        occupationRepository.save(occupation);

        repository.save(table);
        return new ResponseTableDTO(idTable, table.getStatus().toString());
    }

    private void addParticipant(Table table, String user) {
        Occupation occupation = findOccupation(table);
        buildParticipant(occupation, user);
    }

    private Occupation findOccupation(Table table) {
        List<Occupation> occupationList = occupationRepository.findAllByTableAndFinishIsNull(table);
        assert occupationList.size() == 1;
        return occupationList.get(0);
    }

    private void buildOccupation(Table table, String user) {
        Occupation occupation = Occupation.builder().table(table)
                .id(buildId(table.getId() + user + LocalDateTime.now()))
                .initial(LocalDateTime.now()).user(user).build();

        occupation = occupationRepository.save(occupation);

        buildParticipant(occupation, user);
    }

    private void buildParticipant(Occupation occupation, String user) {
        Participant participant = Participant.builder().dateTimeSit(LocalDateTime.now())
                .id(buildId(occupation.getId() + user + LocalDateTime.now()))
                .occupation(occupation).user(user).build();

        participantRepository.save(participant);
    }

    private String buildId(String originalString) {
        return Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }
}
