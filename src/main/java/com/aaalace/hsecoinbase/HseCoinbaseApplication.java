package com.aaalace.hsecoinbase;

import com.aaalace.hsecoinbase.domain.enums.AnalyticsType;
import com.aaalace.hsecoinbase.domain.enums.Command;
import com.aaalace.hsecoinbase.domain.enums.CrudModelType;
import com.aaalace.hsecoinbase.domain.enums.CrudOperation;
import com.aaalace.hsecoinbase.domain.model.bankaccount.BankAccount;
import com.aaalace.hsecoinbase.domain.model.category.Category;
import com.aaalace.hsecoinbase.domain.model.category.CategoryType;
import com.aaalace.hsecoinbase.domain.model.operation.Operation;
import com.aaalace.hsecoinbase.domain.model.operation.OperationType;
import com.aaalace.hsecoinbase.repository.BankAccountRepository;
import com.aaalace.hsecoinbase.repository.CategoryRepository;
import com.aaalace.hsecoinbase.service.AnalyticsService;
import com.aaalace.hsecoinbase.service.AnalyticsServiceFactory;
import com.aaalace.hsecoinbase.service.ModelCrudFacade;
import com.aaalace.hsecoinbase.util.CommandUtil;
import com.aaalace.hsecoinbase.util.SessionLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.aaalace.hsecoinbase.domain.enums.CrudOperation.*;

@Slf4j
@SpringBootApplication
public class HseCoinbaseApplication implements CommandLineRunner {

    private final BufferedReader reader;
    private final ModelCrudFacade modelCrudFacade;
    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;
    private final AnalyticsServiceFactory analyticsServiceFactory;

    public HseCoinbaseApplication(
            ModelCrudFacade modelCrudFacade,
            BankAccountRepository bankAccountRepository,
            CategoryRepository categoryRepository,
            AnalyticsServiceFactory analyticsServiceFactory
    ) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.modelCrudFacade = modelCrudFacade;
        this.analyticsServiceFactory = analyticsServiceFactory;

        // for demo only ------
        this.bankAccountRepository = bankAccountRepository;
        this.categoryRepository = categoryRepository;
        // ------ for demo only
    }

    public static void main(String[] args) {
        SpringApplication.run(HseCoinbaseApplication.class, args);
    }

    @Override
    public void run(String... args) {
        SessionLogger sl = new SessionLogger(UUID.randomUUID());
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = sl::log;
        scheduler.scheduleAtFixedRate(task, 3, 200, TimeUnit.SECONDS);

        log.info("Start hsecoinbase app");
        this.poll();
        log.info("Shut down hsecoinbase app");
        scheduler.shutdown();
    }

    private void poll() {
        while (true) {
            CommandUtil.outCommands();
            try {
                Command command = CommandUtil.getCommandById(reader.readLine());
                switch (command) {
                    case BANK_ACCOUNT_MENU -> this.crudPoll(CrudModelType.BANK_ACCOUNT);
                    case CATEGORY_MENU -> this.crudPoll(CrudModelType.CATEGORY);
                    case OPERATION_MENU -> this.crudPoll(CrudModelType.OPERATION);
                    case COUNT -> this.analytics(AnalyticsType.COUNT);
                    case PNL -> this.analytics(AnalyticsType.PNL);
                    case EXIT -> { return; }
                }
            } catch (Exception e) {
                log.error("Error processing request: {}", e.getMessage());
            }
        }
    }

    private void analytics(AnalyticsType analyticsType) {
        AnalyticsService service = analyticsServiceFactory.getService(analyticsType);
        service.fetchData("");
    }

    private void crudPoll(CrudModelType type) throws Exception {
        UUID objId = UUID.randomUUID();

        CommandUtil.outCrudOps();
        Command command = CommandUtil.getCommandById(reader.readLine());
        CrudOperation op = switch (command) {
            case GET_COMMAND -> {
                System.out.println("Enter id: ");
                objId = UUID.fromString(reader.readLine());
                yield GET;
            }
            case ADD_COMMAND -> CREATE;
            case UPD_COMMAND -> {
                System.out.println("Enter id: ");
                objId = UUID.fromString(reader.readLine());
                yield UPDATE;
            }
            case DEL_COMMAND -> {
                System.out.println("Enter id: ");
                objId = UUID.fromString(reader.readLine());
                yield DELETE;
            }
            default -> throw new IllegalStateException("Unexpected value: " + command);
        };

        Random random = new Random();
        var entity = switch (type) {
            case BANK_ACCOUNT -> BankAccount.builder()
                    .id(objId)
                    .name(UUID.randomUUID().toString())
                    .balance(BigDecimal.valueOf(1000))
                    .build();
            case CATEGORY -> Category.builder()
                    .id(objId)
                    .name(UUID.randomUUID().toString())
                    .type(random.nextInt() % 2 == 0 ? CategoryType.Income : CategoryType.Outcome)
                    .build();
            case OPERATION -> {
                // for demo only ------
                BankAccount account = bankAccountRepository.findAll().get(
                        random.nextInt(bankAccountRepository.findAll().size())
                );
                Category category = categoryRepository.findAll().get(
                        random.nextInt(categoryRepository.findAll().size())
                );
                // ------ for demo only

                yield Operation.builder()
                        .id(objId)
                        .type(random.nextInt() % 2 == 0 ? OperationType.Income : OperationType.Outcome)
                        .amount(BigDecimal.valueOf(random.nextDouble() * 100))
                        .description("description")
                        .bankAccountId(account.getId())
                        .categoryId(category.getId())
                        .build();
            }
        };

        modelCrudFacade.performCrudOperation(type, op, entity);
    }
}
