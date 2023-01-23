package ma.centralbank.services.client;

import ma.centralbank.dto.AccountOperationResponseDto;
import ma.centralbank.dto.BankAccountDto;
import ma.centralbank.dto.CinDto;
import ma.centralbank.dto.UserDto;
import ma.centralbank.reqObjects.RegisterForm;
import ma.centralbank.enums.AccountStatus;
import ma.centralbank.enums.OperationType;
import ma.centralbank.models.AccountOperation;
import ma.centralbank.models.BankAccount;
import ma.centralbank.models.Cin;
import ma.centralbank.models.User;
import ma.centralbank.repository.accountOperation.AccountOperationRepository;
import ma.centralbank.repository.bankAccount.BankAccountRepository;
import ma.centralbank.repository.cin.CinRepository;
import ma.centralbank.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    UserRepository userRepository;
    BankAccountRepository bankAccountRepository;
    CinRepository cinRepository;
    ModelMapper modelMapper;
    AccountOperationRepository accountOperationRepository;

    public ClientServiceImpl(UserRepository userRepository, BankAccountRepository bankAccountRepository, CinRepository cinRepository, ModelMapper modelMapper, AccountOperationRepository accountOperationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.cinRepository = cinRepository;
        this.modelMapper = modelMapper;
        this.accountOperationRepository = accountOperationRepository;
        this.passwordEncoder = passwordEncoder;
    }



    private PasswordEncoder passwordEncoder;

    private final String path = "C:\\Users\\Youcode\\Desktop\\briefs2\\CentralBank-Spring-Boot--Spring-Security--JWTS\\CentralBank\\src\\main\\resources\\static\\cins\\";



    @Override
    public User addClient(User user) {
        String pw = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(pw));
        return this.userRepository.save(user);
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        return this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public Cin addCin(MultipartFile file,User user) throws IOException {
        String filePath = this.path+file.getOriginalFilename();
        Cin cin  = Cin.builder().name(file.getOriginalFilename()).user(user).build();
        file.transferTo(new File(filePath));
        return this.cinRepository.save(cin);
    }

    @Override
    public void register(RegisterForm registerForm) throws IOException {

        Random random = new Random();
        long randomNumber = Math.abs(random.nextLong());
        String randomNumberStr = String.format("%024d", randomNumber);
        System.out.println(Long.valueOf(randomNumberStr));

        User user = this.addClient(registerForm.getUser());
        Cin cin = this.addCin(registerForm.getFile(),user);
        BankAccount bankAccount = this.createBankAccount(BankAccount.builder()
                .accountNumber(randomNumber)
                .creationDate(LocalDate.from(LocalDateTime.now()))
                .customer(user)
                .status(AccountStatus.INACTIVE)
                .balance(0).build());
    }

    @Override
    public boolean makeTransaction(String emailSenderBankAccount, Long idReceiverBankAccount, double amount) {
        User user = this.userRepository.findByEmail(emailSenderBankAccount);
        Optional<BankAccount> sender = Optional.ofNullable(this.bankAccountRepository.findByUser_Id(user.getId()));
        BankAccount receiver = this.bankAccountRepository.findByUser_Id(idReceiverBankAccount);

        if(sender.isPresent() && receiver!=null){
             this.accountOperationRepository.save(AccountOperation.builder()
                    .amount(amount)
                    .operationDate(LocalDate.from(LocalDateTime.now()))
                    .bankAccount(receiver)
                     .secondBankAccount(sender.get())
                    .type(OperationType.DEBIT)
                    .build());

            this.accountOperationRepository.save(AccountOperation.builder()
                    .amount(amount)
                    .operationDate(LocalDate.from(LocalDateTime.now()))
                    .secondBankAccount(receiver)
                    .bankAccount(sender.get())
                    .type(OperationType.CREDIT)
                    .build());
            return true;
        }

        return false;
    }

    @Override
    public boolean makeTransfer(Long idBankAccount, double amount) {
        Optional<BankAccount> bankAccount = this.bankAccountRepository.findById(idBankAccount);

        if(bankAccount.isPresent()){
            this.accountOperationRepository.save(AccountOperation.builder()
                    .bankAccount(bankAccount.get())
                    .amount(amount)
                    .type(OperationType.DEBIT)
                    .operationDate(LocalDate.from(LocalDateTime.now()))
                    .build());
            bankAccount.get().setBalance(amount);
            return true;
        }



        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public BankAccountDto getBankAccount(String email) {
        User user = this.getUserByEmail(email);
        Cin cin = user.getCin();
        CinDto cinDto = this.modelMapper.map(cin,CinDto.class);

        BankAccount bankAccount = this.bankAccountRepository.findByUser_Id(user.getId());
        UserDto userDto = this.modelMapper.map(bankAccount.getCustomer(),UserDto.class);
        BankAccountDto bankAccountDto = this.modelMapper.map(bankAccount,BankAccountDto.class);
        bankAccountDto.setUserDto(userDto);
        bankAccountDto.getUserDto().setCinDto(cinDto);
        return bankAccountDto;

    }

    @Override
    public List<AccountOperationResponseDto> getClientOperations(String email) {
        User user = this.userRepository.findByEmail(email);
        BankAccount bankAccount = this.bankAccountRepository.findByUser_Id(user.getId());
        List<AccountOperation> byBankAccount = this.accountOperationRepository.findByBankAccount(bankAccount);

        List<AccountOperationResponseDto> dtos = new ArrayList<AccountOperationResponseDto>();
        byBankAccount.forEach(el->{
            AccountOperationResponseDto map = this.modelMapper.map(el, AccountOperationResponseDto.class);
            dtos.add(map);
        });

        return dtos;
    }


}
