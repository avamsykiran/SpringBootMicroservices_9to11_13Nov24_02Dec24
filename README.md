Spring Boot Microservices
--------------------------------------------

    Topics To Be Covered        
        Spring Boot initialization, Dependency Injection
        REST Web Service architecture , HTTP Endpoint handling
        Cross origin, Errors and Exception Handling,
        Building custom response using Response Entity

        Validating input request using javax.validation
        
        Logging, Lombak

        Eurekha Registry, Zuul Server / Spring Boot Api Gateway, Feign Client ,Ribbon / Spring Boot Load Balancer

    Lab Setup    
        JDK 17
        STS latest (IDE)
        Maven 3
        Spring 6 and Spring Boot 3

    Pre-Requisites
        Java 8
        JPA ORM
        API vs Framework vs Library
        Maven Build Tool
        Spring Core, SpEL

    Spring Frameork
    ---------------------------------------------------------------------

        is a highly organized development platform for Java Enterprise Applications.

        Spring is modular

            Spring Core                         is a must-have for all other spring modules
            Spring Beans                        offers DI and a container called BeanFactory
            Spring Context                      offers advacned DI and a container called ApplicationContext
            Spring Expression Language          offers expression language to refer to complicated dependences or internationalization
            Spring AOP                          offers Aspect Oriented Programming
            Spring Data                         offers automated implementations of ORM interfaces
            Spring Web                          offers support to develop Web MVC app and REST api or Async Apis or Async Web Apps
            Spring Batch                        offers support to develop batch processing app
            Spring Security                     offers authentication and authorization for any spring app.
            Spring Test                         offers a testing framework for all spring app.
            Spring Boot                         offers automated configuration and Rapid Application Development.
            ....etc.,

        Spring Offers DI

            Techniques to ensure maintainability: 

                For an app to be maintainable, it has to exibhit highest cohesion and loose coupling.

                1. Modularization               ensures cohesion
                2. Multi-Layer Archetecture     ensures loose coupling
                3. Dependency Injection         enhances loose coupling

            Cosnider an Operation - Search for an Employee given an employee id

                (a) accept an employee id
                (b) validate the employee id
                (c) query for that employee record in the database
                (d) display the employee details if found
                (e) display a 'not found' message if not found

            Multi-Layer Archetecture 
            
                DAO     <-----model----->    SERVICE     <--------model---------->  UI
                                                                                    (a) accept an employee id
                                            (b) validate the employee id
                (c) query for that employee 
                    record in the database
                                                                                    (d) display the employee details if found      
                                                                                    (e) display a 'not found' message if not found
                
                interface EmployeeDAO       interface EmployeeService
                class EmployeeDAOImpl       class EmployeeServiceImpl

                interface EmployeeDAO{
                    void add(Employee emp);
                    void delete(long empId);    
                }

                class EmployeeDAOImpl implement EmployeeDAO {
                    //we will create methods to interact with the database
                    //void add(Employee emp)
                    //void delete(long empId)
                }      
                
                class EmployeeDAOJpaImpl implement EmployeeDAO {
                    //we will create methods to interact with the database
                    //void add(Employee emp)
                    //void delete(long empId)
                }      
                
                interface EmployeeService {

                }

                class EmployeeServiceImpl implements EmployeeService {

                    private EmployeeDAO empDao;

                    public EmployeeServiceImpl(){
                        //this.empDao = new EmployeeDaoImpl();
                        this.empDao = new EmployeeDaoJpaImpl();
                    }

                    //we will have methods thet do computations or validations and call dao methods when needed
                    //dao.add(emp)
                    //dao.delete(empId)
                }
        
            Dependency Injection 

                Dependency is a software component (part) that is being used
                Dependent is a software component that uses another.

                DAO is a dependency for SERVICE and SERVICE is a dependent of DAO.

                Dependency Injection is injecting dependency into dependent.

                interface EmployeeDAO{
                    void add(Employee emp);
                    void delete(long empId);    
                }

                class EmployeeDAOImpl implement EmployeeDAO {
                    //we will create methods to interact with the database
                    //void add(Employee emp)
                    //void delete(long empId)
                }      
                
                class EmployeeDAOJpaImpl implement EmployeeDAO {
                    //we will create methods to interact with the database
                    //void add(Employee emp)
                    //void delete(long empId)
                }      
                
                interface EmployeeService {

                }

                class EmployeeServiceImpl implements EmployeeService {

                    private EmployeeDAO empDao;

                    public EmployeeServiceImpl(EmployeeDAO empDao){
                        this.empDao = empDao;
                    }

                    public EmployeeServiceImpl(){

                    }

                    public void setEmpDao(EmployeeDAO empDao){
                        this.empDao = empDao;
                    }

                    //we will have methods thet do computations or validations and call dao methods when needed
                    //dao.add(emp)
                    //dao.delete(empId)
                }

                //Dependency Injection via Constructor - Constructor Injection                
                EmployeeService empService1 = new EmployeeServiceImpl(new EmployeeDaoImpl());
                EmployeeService empService2 = new EmployeeServiceImpl(new EmployeeDaoJpaImpl());

                //Dependency Injection via Setter - Setter Injection                
                EmployeeService empService3 = new EmployeeServiceImpl();
                empService3.setEmpDao(new EmployeeDaoJpaImpl());


        Spring Containers

            a spring container is a piece of software that can create objects to software components
            and manage the life cycle of such objects and automate dependency injection.

            Such objects being managed by the CONTAINER are called BEANS.

            CONTAINER create and manages BEANS of COMPONENTS.

            We have two major spring containers 
                BeanFactory                 Spring Beans Module
                ApplicationContext          Spring Context Module

            Bean Configuration is the machanisim via which the list of components and their dependencies are informed to the CONTAINER.

                1. XML Based Configuration
                2. Annotation Based Configuration
                3. Java Based Configuration

            Annotation Based Configuration

                Step 1. create a configuration class

                        @Configuration
                        public class MyBeanConfig {

                        }

                Step 2. inform the base package from where components must be picked. and also propertysource
                        
                        @Configuration
                        @ComponentScan("base.package")
                        @ProeprtySource("the location of the properties file")
                        public class MyBeanConfig {

                        }

                Step 3. Mark components

                        @Component
                            |- @Service
                            |- @Repository
                            |- @Controller
                            |- @Advise
                            |- @RestController
                            |- @ControllerAdvise
                            |- @RestControllerAdvise
                            ....etc.,

                        interface EmployeeDAO {

                        }

                        @Repository
                        class EmployeeDAOImpl implements EmployeeDAO{

                        }

                        interface EmployeeService{

                        }

                        @Service
                        class EmployeeServiceImpl implements EmployeeService {

                        }

                Step 4. Mark Dependencies

                        @Service
                        class EmployeeServiceImpl implements EmployeeService {
                            
                            @Autowired
                            private EmployeeDAO empDao;

                            @Value("${hra.percent}")
                            private double hraPercent;
                        }

                Step 5. Create the container and load the configuration.

                        ApplicationContext
                            |- AnnotationConfigApplicationContext
                            |- AnnotationConfigWebApplicationContext
                            |- XmlWebApplicationContext
                            |- ClassPathXmlpplicationContext
                            ...etc.,

                        ApplicationContext context = new AnnotationConfigApplicationContext(MyBeanConfig.class);

            Java Based Configuration
            
                        @Configuration
                        @ComponentScan("base.package")
                        @ProeprtySource("the location of the properties file")
                        public class MyBeanConfig {

                            @Bean
                            public Scanner scan(){
                                return new Scanner(System.in);
                            }

                        }

                        @Component
                        public class EmployeeScreen{

                            @Autowired
                            private Scanner scan;
                        }
            
            Bean Id is an bean identity to reqeust for the bean when needed.

            Object Naming Notation of a class name is the default bean id.

            For Example, HomeScreen class, homeScreen is the beanId
                         EmployeeServiceImpl class, employeeServiceImpl is the beanId.

            We can override the defautl beanId with our own bean id in the @Component("ourOwnBeanId") 
            or other derived annotations of @Component.

            For Java Based Config, the method name which returns the bean will be the bean Id.

                @Bean
                public Scanner scan(){
                    return new Scanner(System.in);
                }

                //'scan' is the bean id


        Autowiring

            is a process where the contiaenr detects dependecies and maps them to the depandants automatically.

            through autowiring spring contianers support 
                1. Field Injection

                    @Autowired
                    private Type field;

                2. Constructor Injection

                    @Autowired
                    public EmployeeServiceImpl(Type arg1,Type arg2){

                    }

                3. Setter Injection
                    @Autowired
                    public void setField(Type field){
                        this.field=field;
                    }

            Autowiring Types
                No
                ByType          @Autowired
                ByName          @Autowired and @Qualifier("beanId")
                Constructor     while in xml based config, if this option is choosedn, 
                                the constructor with the max number of params is called for DI
                Autodetect      while in xml based config, if this option is choosedn, 
                                spring tries constuctor injection if it fails, byType autowiring is done.

        Bean Scope

            @Scope("singletion|prototype|session|request|global-session")

            bydefault - scope of any bean is singleton

            singletion      injects the same bean for multiple injections
            prototype       injects the a new bean for each injection in multiple injections

            reqeust                 in case of a web-application, a new bean for each request
            session                 in case of a web-application, a new bean for each session
            global-session          in case of a web-application, a new bean for each servlet-context or application.

    Spring Expression Language
    -------------------------------------------------

        https://www.baeldung.com/spring-expression-language
        
    Spring Boot
    -------------------------------------------------

        is a spring module that offers auto-configuration and embeded servers.

        Auto Configuration

            Boiler Plate code for any Spring Context app is
                1. @Configuration, @ComponentScan and @PropertySource annotations
                2. Creating a container and loading the config class into it

            In case of Spring Boot, these steps are not needed. 
            Every Spring Boot Project (Spring Starter Project) comes with a main class annoted as
                
                @SpringBootApplication  = 
                                            @Configuration + 
                                            @ComponentScan("theCurrentPackage") + 
                                            @PropertySource("classpath:application.properties") +
                                            @EnableAutoConfiguration

            In the public static void main, the below line of code will take care of container creating and loading configs.

                SpringApplication.run(MainClass.class, args);

            Simillarly, boiler plate coding for Spring Web Module is
                1. Create a ViewResolver and initiate the ViewResolver proeprties
                2. Map the context root to the FrontController (DispatcherServlert)
                3. Map all '.css', '.html' and other static content like images, audios and videos to a static route

            In case of Spring Boot, if Spring starter Web dependency is included in the pom.xml,
            Spring Boot will autoamtically
                1. configs.., InternalResourceViewResolver with its properties
                2. it maps '/' context root to DispatcherServlet
                3. it maps all static content to '/static/' folder

            And this happens with all other spring modules when used on Spring Boot.

            This offers RAD - Rapid Application Development.

        Embeded Servers

            All the enterprise application need a server either a web-server or a application-server for the
            app to be hosted upon. These servers are radily availale embeded into the starter modules .

            As a result the application becomes stand-alone executable.

            That makes our applicatiosn ready for containarization.

        SpringApplication.run(MainClass.class, args);
            1. Creates a container
            2. Loads the bean config.,
            3. Execute CommandLineRunners and ApplicationRunners (if any)
            4. Start the embeed server (if any)
            5. Once the Server (if any) gets shutdown, the container is closed and destroyed
        
        CommandLineRunner is an interface having
            public abstract void run(String arg[])

        ApplicationRunner is an interface having
            public abstract void run(ApplicationArg arg)
                                                
        Creating a Spring Starter Project (Spring Boot Project)
            1. Using Spring Starter Project wizard of STS
            2. Using Spring initilizr at https://start.spring.io
            3. Using Spring Boot CLI

    Spring Data JPA
    -------------------------------------------------

        is a spring module that offer dynamically auto-implemented Respositories/DAOs (JPA).

        JPA - Java Persistence API  -   javax.persistence.* (JEE 8 or Earlier) - jakarta.persistence.* (JEE 9 or above)

        JPA Annotations
            @Entity
            @Table(name="table_name")

            @Column(name="",nullable=,unique=)
            @Transiant
            @Id

            @Embededable
            @Embeded
            @EmbededId

            @OneToOne
            @OneToMany
            @ManyToOne
            @ManyToMany

            @JoinColumn
            @JoinTable

            @Inheretence(strategy = SINGLE/JOINED/TABLE_PER_CLASS)
            @DiscriminatorColumn
            @DiscriminatorValue

        Spring Data JPA Offers an interface

            CrudRepository
                |
                |- JpaRepository
                        Entity save(Entity)
                        void deleteById(id)
                        boolean existsById(id)
                        Optional<Entity> findById(id)
                        List<Entity> findAll()

        Once we crete our entity class, for a repository we have to create an interface that extends JpaRepository

            @Entity
            @Table(name="contacts")
            public class Contact{

                @Id
                private Long contactId;
                private String fullName;
                private String mobile;
                private String emailId;
                private LocalDate dateOfBirth;

                //constructors and getter and setter and hashcode and equals and toString
            }

            public interface ContactRepository extends JpaRepository<Contact,Long> {

            }

        Adding custom methods to the repository and get them auto-implemented.

            1. a method that returns boolean and whose name composes of 'existBy' clause followed by field name
                is implemented as a search query

            2. a method that returns Optional<Entity> and whose name composes of 'findBy' clause followed by field name
                is implemented as a search query and returns the found record            
                
            3. a method that returns List<Entity> and whose name composes of 'findAllBy' clause followed by field name
                is implemented with a 'select' query    
                
            4. Any mehtod marked with @Query("JPQL") is implemented to execute the given JPQL query

            public interface ContactRepository extends JpaRepository<Contact,Long> {
                
                boolean existsByMobile(String mobile);
                Optional<Contact> findByMobile(String mobile);
                Optional<Contact> findByEmailId(String emailId);
                List<Contact> findAllByFullName(String fullName);

                @Query("SELECT c FROM Contact c WHERE c.dateOfBirth BETWEEN :start AND :end")
                List<Contact> getAllBornInRange(LocalDate start,LocalDate end);
            }

        Spring Data Database Config

            in the .properties file we will have config., For MySQL
                spring.datasource.url = jdbc:mysql://localhost:3306/addb
                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username = root
                spring.datasource.password = root
                spring.jpa.show-sql = false
                spring.jpa.hibernate.ddl-auto = update
                spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

            in the .properties file we will have config., For h2Db
               spring.datasource.url=jdbc:h2:mem:testdb
               spring.datasource.driverClassName=org.h2.Driver
               spring.datasource.username=sa
               spring.datasource.password=password
               spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
                        
            in the .properties file we will have config., For h2Db
               spring.datasource.url=jdbc:h2:file:/data/demo
               spring.datasource.driverClassName=org.h2.Driver
               spring.datasource.username=sa
               spring.datasource.password=password
               spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        
    Spring Web
    ----------------------------------------------------

        is a spring module that supports Web MVC Applications , REST api and other http server based
        applications.

        REST api

            REpresentational State Transfer api is a type of web service.
            
            A Web Service is a method (or a service) hosted on a server and is invoked through a request.

                                                <---------> Angular App
                                                <---------> Andriod App    
            Database    <------->  WEB SERVICE  <---------> Web App
                                                <---------> IOS App
                                                <---------> ReactJS App

            PERSISTENCE          BUSSINESS LOGIC         USER INTERFACE


            Types Of Web Services
                SOAP Web Servies
                    use xml as media of communication
                    web services can receive parameter in xml and as well respond (return) data in xml

                    - XML is not good enough to encapsulate all sorts of data espacially binary.
                    - SOAP works on simple object access protocol which lack proper standards for
                        error or sucess notifications.

                REST api
                    is http protocol based. HTTP protocol is a very old and widly accepted 
                    protocol and is capable of transfering any sort of media, may it be
                    xml/json/binary/images/....etc.,

                    HTTP protocol already has a proven communication standard through Http-Status.

        Rest API Stndards

            Rest api are resource centric. A resource is any domain entity the rest api has to work with.
            for example, Employee / Student / Consumer / ...etc., are resources.

            Any application at the end of the day will perform CRUD operations only. REST api standards
            promote one end-point (url) per resource.

                Employees   /emps
                Students    /students
                Consumers   /consumers ...etc.,

            REST api standards have mapped each HTTP-METHOD (GET/POST/...etc) to a crud operation.
                                                                                                STATUS CODES
            Operations  Example-Url     Http-Method Req-Body        Resp-Body       SUCCESS         CLIENT-SIDE-FAIL    SERVER-SIDE-FAIL
            ------------------------------------------------------------------------------------------------------------------------------
            Create      /emps           POST        emp-Obj.json    empObj.json     201-CREATED     400-BAD REQEUST 500-INTER-SERVER-ERR
            Retrival    /emps           GET         NONE            empArray.json   200-OK          NONE            500-INTER-SERVER-ERR
                        /emps/101       GET         NONE            empObj.json     200-OK          404-NOT FOUND   500-INTER-SERVER-ERR
            Update      /emps           PUT         emp-Obj.json    empObj.json     202-ACCEPTED    400-BAD REQEUST 500-INTER-SERVER-ERR
            Delete      /emps/101       DELETE      NONE            NONE            204-NO CONTENT  404-NOT FOUND   500-INTER-SERVER-ERR

        Spring Web MVC Dynbamic Application

            Spring Web follows single front-controller MVC archetecture.

            Repos   <----model----->  Services    <---model--->    Controller(s)    <------- FrontController <---REQ----  CLIENT
                                                                    |
                                                                    | model
                                                                    |
                                                                    ↓
                                                                    VIEW(s) ---------------------RESP------------------->

        Spring Web - Rest Api

            Spring Web follows single front-controller archetecture.

            Repos   <----model----->  Services    <--model---->    RestControllers    <-------> FrontController <---------->  CLIENT

            
            A Controller is  class that manages the flow of control.
                                (a) receive the reqeust
                                (b) use the required service and other componetns for processign the request
                                (c) generate a response

            A FrontController is a spring web feature that automates the receving of a request and extracting data from a request
            and then it passes the data to an relavent controller so tha the controller can procced processing it. DispatcherServlet of spring acts as the FrontController.

            A controller generally forwards the proccessed data as MODEL to a VIEW (html page or a jsp page or any other page).

            Whereas a RestController passes the data directly in the response as response-body.

            @RestController
            @ReqeustMapping(url,httpMethod)
                |- @GetMapping(url)
                |- @PutMapping(url)
                |- @DeleteMappoing(url)
                |- @PostMApping(url)

            ResponseEntity<M>

                is a class that encaposulates response-body (model) and http-status.

                ResponseEntity(HttpStatus status)
                ResponseEntity(M respBody,HttpStatus status)
                
    Java Validation Specification - javax.validators
    -------------------------------------------------------------

        This specification has proposed a list of annotation to trigger server side validation.

            @Valid              is applied on a model/entity object to trigger validations

            @NotNull

            @NotBlank
            @Size
            @Pattern

            @Past
            @Future
            @Present
            
            @Min
            @Max

            @Email
            ......etc.,

    Spring Boot Testing
    -----------------------------------------------------------------

        Unit Testing

            Testing a specific software component isolatedly.

            Mocking plays a very important role in unit test cases.

            We will mock a repository when unit testing a service,
            we will mock a service when unit testing a controller.

            @ExtendWith(SpringExtension.class)
            @WebMvcTest(EmployeeRestController.class)       //it also supplies MockMvc 
            @TestConfiguration                              //used to repalce the actual-bean-config with our own test-config
            @DataJpaTest                                    //config test database and provides TestEntityManager
            
            @MockBean
            
        Integration Testing

            @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,classes = Application.class)
            
    Spring Boot Dev Tools
    -------------------------------------------------------------

        is a spring starter module 'Development Tools' to support the server management
        whiled the app is in development stage.

        1. Autorestart: If any chagne occurs to the code base, the code when saved will trigger
            an automatic compilation, packaging, hosting and restart of the server.

        2. Live reload: When working web mvc apps, chagnes to static files like images/html/css/js will refresh
            the browser automatically

        3. Remote development support: the local dev env can be linmked or connected to a remote server

        4. Logging, monitoring like observation taks are also provided as built-in development support.

    Monolythic Apporach vs MicroServices
    -----------------------------------------------
        
        Monolythic Apporach:   One App - One Single Unit - One Deployment File (.jar,.war,..etc.,). 
                The entire solutions is in one single application which generally is organized into
                modules for easier maintanence.

        Microservices:  One Ecos System - Interdependent Individual Services. 
                        Each microservices may represent a specific feature of the solution equivalent to a module.

                (+) Isolated Scaling
                (+) Simless Technical Upgradations
                (+) RAD

                >> Decomposition and Design
                >> Inter Service Communication
                >> Distributed Tracing
                >> Distrubted Transactions
                and so on...

    Microservice Design Patterns
    ------------------------------------------------

        Decomposition by Domain                         solve the design chanllenge
        Decomposition by Sub-Domain
        
        Discovery Service Design Pattern                solve the inter service communiction challenge
        API Gateway Design Pattern
        Aggregator Design Pattern

        Distributed Tracing Service Design Pattern      solve Observability and tracing challenge
        Log Aggregator Design Pattern

        CQRS Design Pattern                             solve distributed transctions cjhallenge
        Saga Design Pattern

        External Configuaration Design Pattern          solve cross cutting issues
        Circuit Breaker Design Pattern

    
    Decomposition by Domain
    -----------------------------------------------------
        Is that , baring acute knowledge of the domain of an APP, the independent features of the application
        can be identified as microservices.

            Case Study - BudgetTrackingApp
                An individual can register himself/herself and record his earnings and spendings and the system has to 
                generate statements periodically .

            profile microservice        to allow an account-holder to register
            txns microservice           to allow a registered account-holder to record his transactions (earnings/spendings0)
            statement microservice      to generate a statement for an account-holder given a period.

    
    Decomposition by sub-Domain
    -----------------------------------------------------
        Is that , identify the god-classes and define their context-boundaries.

            Case Study - BudgetTrackingApp
                
                profile microservice        to allow an account-holder to register
                    AccountHolder Entity
                        ahId : Long
                        fullName : String
                        mobile : String
                        mailId : String

                txns microservice           to allow a registered account-holder to record his transactions (earnings/spendings0)
                    AccountHolder Entity
                        ahId : Long
                        currentBalance : Double
                        txns : Set<Txn>

                    Txn Entity
                        txnId : Long
                        header : String
                        amount : Double
                        txnType : TxnType (CREDIT/DEBIT)
                        txnDate : LocalDate
                        holder : AccountHolder

                statement microservice      to generate a statement for an account-holder given a period.
                    Statemetn Model
                        account : AccoutnHolder
                        txns : Set<Txn>
                        startDate : LocalDate
                        endDate : LocalDate
                        totalCredit : Double
                        totalDebit : Double

                    AccountHolder Model
                        ahId: Long
                        fullName : String
                        mobile : String
                        mailId : String
                        currentBalance : Double
                    
                    Txn Model
                        txnId : Long
                        header : String
                        amount : Double
                        txnType : TxnType (CREDIT/DEBIT)
                        txnDate : LocalDate

    Discovery Service Design Pattern 
    -----------------------------------------

        A discovery service serves as a stable point of reference for the entire eco-ssystem. 
        It manages a registry where each time an instance of any microservice starts, it is registered.
        When Microservice-A needs to request Microservice-B, Microservice-A can get the address of Ms-B from discovery-service.

         discovery-service
        (Netflix Eureka Server)
            ↑↓
            ||
            ||
            ||================================================
                    ↑↓                  ↑↓                  ↑↓
                profile-service        txns-service        stateemnt-service

    API Gateway Service Design Pattern 
    -----------------------------------------

        An api-gateway is another microservice that acts as a single point-of-contact to the eco-system for
        any client 

         discovery-service      <------------------------>  api-gateway <------REQ's------ CLIENT (SPA APP/ WEB APP)
        (Netflix Eureka Server)                         (zuul / spring cloud api gateway)
            ↑↓                                                  ↑↓
            ||                                                  ||
            ||                                                  ||
            ||==================================================||
                    ↑↓                  ↑↓                  ↑↓
                profile-service        txns-service        stateemnt-service
                    ↓                       ↓                   ↓
                    ----------------------------------------------------------RESP---------→ CLIENT (SPA APP/ WEB APP)

    