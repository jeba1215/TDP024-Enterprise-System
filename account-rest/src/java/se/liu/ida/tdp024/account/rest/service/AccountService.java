package se.liu.ida.tdp024.account.rest.service;

import java.util.List;
import java.util.Vector;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
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
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;

@Path("/account")
public class AccountService {

    // --- Here we choose the implementations of the logic and data layer --- //
    private final AccountLogicFacade AccountLogicFacade =
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    //----------------------------------------------------------------------- //

    private static final AccountLogger accountLogger = new AccountLoggerImpl();
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
        long id = AccountLogicFacade.create(accounttype, name, bank);
        
        return Response.ok().entity(id + "").build();        
    }
    
    @GET
    @Path("find")
    public Response find(@QueryParam("name") String name) {
        System.out.println("----- find -----");
        
        List<Account> vAccount = AccountLogicFacade.find(name);
        
        if (vAccount != null) {

            String json = jsonSerializer.toJson(vAccount);

            return Response.status(Response.Status.OK).entity(json).build();

        } else {

            accountLogger.log(
                    AccountLogger.TodoLoggerLevel.WARNING,
                    "No Account found for id",
                    "We could not find an Account for name: " + name);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
    @GET
    @Path("debit")
    public Response debit() 
    {
        return Response.ok().build();
    }
    
    @GET
    @Path("credit")
    public Response credit() 
    {
        return Response.ok().build();
    }
    
    @GET
    @Path("transactions")
    public Response transactions() 
    {
        return Response.ok().build();
    }
    
}
