package test.domain;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestAntragsRuhestand.class, TestDienstunfaehigkeitRuhestand.class, TestEngagierterRuhestand.class, 
	TestRegelaltersgrenzeRuhestand.class, TestVorruhestand.class, TestDienstunfall.class})
public class TestSuiteDomain {
}