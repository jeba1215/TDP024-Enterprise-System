package se.liu.ida.tdp024.account.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static junit.runner.Version.id;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerMonlog;


@Path("/account")
public class AccountService {  

    // --- Here we choose the implementations of the logic and data layer --- //
    private final AccountLogicFacade AccountLogicFacade =
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private final TransactionLogicFacade TransactionLogicFacade =
            new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    //----------------------------------------------------------------------- //

    private static final AccountLogger accountLogger = new AccountLoggerMonlog();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
    @GET
    @Path("/")
    public Response index() 
    {
        return Response.ok().build();
    }

    @GET
    @Path("create")
    public Response create(@QueryParam("accounttype") String accounttype, @QueryParam("name") String name, @QueryParam("bank") String bank) 
    {
        String status = AccountLogicFacade.create(accounttype, name, bank);
        if(status.equals("FAILED")){      
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "No Account could be created",
                    "We could not create an account for: " + name);
        }
        return Response.ok().entity(status).build();
    }
    
    @GET
    @Path("find/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@QueryParam("name") String name) {
        System.out.println("----- find -----");
        
        List<Account> vAccount = AccountLogicFacade.find(name);
        
        GenericEntity<List<Account>> list = new GenericEntity<List<Account>>(vAccount) {
        };
        
        if (vAccount != null) {
            return Response.status(Response.Status.OK).entity(list).type(MediaType.APPLICATION_JSON).build();
        } else {
            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "No Account found for id",
                    "We could not find an Account for name: " + name);
            return Response.status(Response.Status.OK).entity("[]").build();
        }

    }
    
    @GET
    @Path("debit")
    public Response debit(@QueryParam("id") Long id, @QueryParam("amount") long amount) 
    {   
        String status = TransactionLogicFacade.debit(id, amount);
        return Response.ok().entity(status).build();
    }
    
    @GET
    @Path("credit")
    public Response credit(@QueryParam("id") Long id, @QueryParam("amount") long amount) 
    {
        String status = TransactionLogicFacade.credit(id, amount);
        return Response.ok().entity(status).build();
    }
    
    @GET
    @Path("transactions")
    public Response transactions(@QueryParam("id") Long id) 
    {
        System.out.println("----- find -----");
        
        List<Transaction> vTransaction = TransactionLogicFacade.find(id);
        
        if (!vTransaction.isEmpty()) {
            return Response.status(Response.Status.OK).entity(vTransaction).build();
        } else {

            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.CRITICAL,
                    "No Transaction found for id",
                    "We could not find a Transaction for name: " + id);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
}
