package ma.centralbank.ExecptionsHandler;


import ma.centralbank.ExecptionsHandler.ExecptionsData.Execptions;
import ma.centralbank.ExecptionsHandler.ExecptionsData.LimitExecption;
import ma.centralbank.ExecptionsHandler.ExecptionsData.OperationExecption;
import ma.centralbank.enums.CardType;
import org.springframework.stereotype.Component;

@Component
public class AccountOperationsExecptions {


    public Execptions AccountExceptions(Execptions execptions) {
        return execptions;
    }

    public OperationExecption successfuOperations(OperationExecption operationExecption){
        return operationExecption;
    }

    public LimitExecption limitExecption(LimitExecption limitExecption){
        return limitExecption;
    }

}
