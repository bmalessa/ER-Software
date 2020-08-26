package test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.domain.TestSuiteDomain;

@RunWith(Suite.class)
@SuiteClasses({TestSuiteDomain.class})
public class TestSuite {
}