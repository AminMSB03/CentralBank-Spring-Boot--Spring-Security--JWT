package ma.centralbank.controllers;

import ma.centralbank.dto.AccountOperationRequestDto;
import ma.centralbank.dto.AccountOperationResponseDto;
import ma.centralbank.models.BankAccount;
import ma.centralbank.services.client.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OperationsRestController {

    private ClientService clientService;

    public OperationsRestController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/operations")
    public List<AccountOperationResponseDto> getClientOperations(Principal principal){
        List<AccountOperationResponseDto> clientOperations = this.clientService.getClientOperations(principal.getName());
        return clientOperations;
    }

    @GetMapping("/operations/recipients")
    public List<BankAccount> getClientRecipients(Principal principal){
        List<AccountOperationResponseDto> clientOperations = this.clientService.getClientOperations(principal.getName());
        List<BankAccount> BankAccountList = new ArrayList();
        clientOperations.forEach(el->BankAccountList.add(el.getSecondBankAccount()));
        List<BankAccount> bankAccount = BankAccountList.stream().distinct().collect(Collectors.toList());
        return bankAccount;
    }

}
