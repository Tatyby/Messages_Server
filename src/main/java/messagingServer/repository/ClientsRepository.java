package messagingServer.repository;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Data
public class ClientsRepository {
    private Map<Integer,List<Map<Integer, List<String>>>> clientsDialog=new ConcurrentHashMap<>(); //ключ Id клиента, значение это список Map с ключом id сессии и значение это список сообщений
//    private Map<Integer, List<Integer>> clients = new ConcurrentHashMap<>(); // ключ ID клиента, значение - список сессий
//    private Map<Integer, List<String>> dialogs = new ConcurrentHashMap<>(); // ключ ID сессии, значение - список сообщений

}
