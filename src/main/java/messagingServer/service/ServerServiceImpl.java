package messagingServer.service;

import messagingServer.exception.ServerException;
import messagingServer.repository.ClientsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {
    private final ClientsRepository clientsRepository;
    private int clientID = 1;
    private int sessionID = 1;

    public ServerServiceImpl(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public int registerNewClient() {
        if (clientsRepository.getClients().isEmpty()) {
            clientsRepository.getClients().put(clientID, new ArrayList<>());
        } else {
            int lastKey = 0;
            for (Integer key : clientsRepository.getClients().keySet()) {
                lastKey = key;
            }
            clientID = lastKey + 1;
            clientsRepository.getClients().put(clientID, new ArrayList<>());
        }
        return clientID;
    }

    public List<Integer> getAllClients() {
        return new ArrayList<>(clientsRepository.getClients().keySet());
    }

    public Integer startDialog(Integer clientID) {
        if (clientsRepository.getClients().containsKey(clientID)) {
            List<Integer> sessionIDList = clientsRepository.getClients().get(clientID);
            if (sessionIDList.isEmpty()) {
                sessionIDList.add(sessionID);
                clientsRepository.getClients().put(clientID, sessionIDList);
                clientsRepository.getDialogs().put(sessionID, new ArrayList<>());
            } else {
                sessionID = sessionIDList.get(sessionIDList.size() - 1) + 1;
                sessionIDList.add(sessionID);
                clientsRepository.getClients().put(clientID, sessionIDList);
                clientsRepository.getDialogs().put(sessionID, new ArrayList<>());
            }
            return sessionID;
        } else {
            throw new ServerException("Клиента по ID " + clientID + " не найдено");
        }
    }

    public void stopDialog(Integer sessionID) {
        if (clientsRepository.getDialogs().containsKey(sessionID)) {
            clientsRepository.getDialogs().remove(sessionID);
        } else {
            throw new ServerException("Сессии по ID " + sessionID + " не найдена");
        }
    }

    public void sendMessage(Integer sessionID, String msg) {
        if (clientsRepository.getDialogs().containsKey(sessionID)) {
            List<String> messageList = clientsRepository.getDialogs().get(sessionID);
            messageList.add(msg);
            clientsRepository.getDialogs().put(sessionID, messageList);
        } else {
            throw new ServerException("Сессии по ID " + sessionID + " не найдена");
        }
    }

    
    public List<Integer> get() {
        return new ArrayList<>(clientsRepository.getDialogs().keySet());
    }
    public List<String> getMsg(Integer sessionID) {
        return clientsRepository.getDialogs().get(sessionID);
    }
}

