package messagingServer.service;

import lombok.extern.slf4j.Slf4j;
import messagingServer.exception.ServerException;
import messagingServer.repository.ClientsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ClientsRepository clientsRepository;
    private int clientID = 1;
    private int sessionID = 1;

    public ServerServiceImpl(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public int registerNewClient() {
        if (clientsRepository.getClientsDialog().isEmpty()) {
            clientsRepository.getClientsDialog().put(clientID, new ArrayList<>());
        } else {
            int lastKey = 0;
            for (Integer key : clientsRepository.getClientsDialog().keySet()) {
                lastKey = key;
            }
            clientID = lastKey + 1;
            clientsRepository.getClientsDialog().put(clientID, new ArrayList<>());
        }
        log.info("Пользователь зарегистрировался по ID: " + clientID);
        return clientID;
    }

    public List<Integer> getAllClients() {
        return new ArrayList<>(clientsRepository.getClientsDialog().keySet());
    }

    public Integer startDialog(Integer clientID) {
        Map<Integer, List<String>> map = new HashMap<>();
        if (clientsRepository.getClientsDialog().containsKey(clientID)) {
            List<Map<Integer, List<String>>> sessionIDList = clientsRepository.getClientsDialog().get(clientID);
            if (sessionIDList.isEmpty()) {
                map.put(sessionID, new ArrayList<>());
                sessionIDList.add(map);
            } else {
                sessionID = sessionIDList.get(sessionIDList.size() - 1).keySet().iterator().next() + 1;
                map.put(sessionID, new ArrayList<>());
                sessionIDList.add(map);
            }
            return sessionID;
        } else {
            throw new ServerException("Клиента по ID " + clientID + " не найдено");
        }
    }

    public void stopDialog(Integer clientID, Integer sessionID) {
        List<Map<Integer, List<String>>> sessionIDList = clientsRepository.getClientsDialog().get(clientID);
        boolean flag = false;
        for (Map<Integer, List<String>> integerListMap : sessionIDList) {
            if (integerListMap.containsKey(sessionID)) {
                integerListMap.remove(sessionID);
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new ServerException("Сессии по ID " + sessionID + " не найдена");
        }
    }


    public void sendMessage(Integer clientID,Integer sessionID, String msg) {
        List<Map<Integer, List<String>>> sessionIDList = clientsRepository.getClientsDialog().get(clientID);
        boolean flag = false;
        for (Map<Integer, List<String>> integerListMap : sessionIDList) {
            if (integerListMap.containsKey(sessionID)) {
                List<String> messageList = integerListMap.get(sessionID);
                messageList.add(msg);
                integerListMap.put(sessionID,messageList);
                flag = true;
                log.info(messageList.toString());
                break;
            }
        }
        if (!flag) {
            throw new ServerException("Сессии по ID " + sessionID + " не найдена");
        }

    }
}

