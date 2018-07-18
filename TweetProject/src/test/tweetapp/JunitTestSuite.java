package tweetapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tweetapp.service.UserServiceTest;
import tweetapp.util.StringUtilTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceTest.class,
    StringUtilTest.class
})

public class JunitTestSuite {
}
