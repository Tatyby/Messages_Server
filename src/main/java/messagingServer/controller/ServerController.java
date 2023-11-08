package messagingServer.controller;

import io.swagger.v3.oas.annotations.Operation;
import messagingServer.model.MessageDialogRequest;
import messagingServer.service.ServerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {
    private final ServerServiceImpl serverService;

    public ServerController(ServerServiceImpl serverService) {
        this.serverService = serverService;
    }

    @Operation(summary = "Регистрация нового клиента, возвращает присвоенный ID")
    @PostMapping("/register")
    public ResponseEntity<Integer> registerNewClient() {
        return new ResponseEntity<>(serverService.registerNewClient(), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка ID зарегистрированных клиентских приложений")
    @GetMapping("/allClients")
    public ResponseEntity<List<Integer>> getAllClients() {
        return new ResponseEntity<>(serverService.getAllClients(), HttpStatus.OK);
    }

    @Operation(summary = "Начало диалога по ID клиента, возвращает ID сессии")
    @PostMapping("/startDialog/{clientID}")
    public ResponseEntity<Integer> startDialog(@PathVariable Integer clientID) {
        return new ResponseEntity<>(serverService.startDialog(clientID), HttpStatus.OK);
    }

    @Operation(summary = "Завершение и удаление диалога по ID сессии")
    @DeleteMapping("/deleteDialog/{clientID}/{sessionID}")
    public ResponseEntity<?> deleteDialog(@PathVariable Integer clientID,
                                          @PathVariable Integer sessionID) {
        serverService.stopDialog(clientID, sessionID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "отправка сообщения по ID сессии")
    @PostMapping("/sendMessage/{clientID}/{sessionID}")
    public ResponseEntity<?> sendMessage(@PathVariable Integer clientID,
                                         @PathVariable Integer sessionID, @RequestBody MessageDialogRequest messageDialog) {
        serverService.sendMessage(clientID, sessionID, messageDialog.getMsg());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
