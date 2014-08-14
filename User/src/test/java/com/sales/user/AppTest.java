package com.sales.user;

import com.framework.cache.spi.cache.RollBackCache;
import com.framework.context.ApplicationContext;
import com.framework.entity.builder.DAOContextBuilder;
import com.framework.entity.builder.EntityContextBuilder;
import com.framework.entity.builder.ExtendedEntityContextBuilder;
import com.framework.entity.configuration.ProviderConfigurationManager;
import com.framework.entity.context.DAOContext;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
         System.out.println("开始解析dao");
         String[] packages = {"com.sales"};
        DAOContextBuilder daoContextBuilder = new DAOContextBuilder();
        daoContextBuilder.setEntityContextBuilder(new EntityContextBuilder()).setExtendedEntityContextBuilder(new ExtendedEntityContextBuilder()).setProviderConfigurationManager(ProviderConfigurationManager.getInstance()).setPackages(packages).setThreadLocalManager(new RollBackCacheThreadLocalManager<RollBackCache>());
        DAOContext daoContext = daoContextBuilder.build();
    //    ApplicationContext.CTX.setDaoContext(daoContext);
    }
}
