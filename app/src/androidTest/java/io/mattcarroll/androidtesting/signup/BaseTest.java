package io.mattcarroll.androidtesting.signup;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by abzalbek on 9/10/17.
 */

public class BaseTest {

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    // triggering method before tests
    @Before
    public void baseSetUp() throws IOException {
        properties = new Properties();
        AssetManager testAssetManager = InstrumentationRegistry.getContext().getAssets();
        AssetManager.AssetInputStream assetStream = (AssetManager.AssetInputStream) testAssetManager.open("user.properties");
        properties.load(assetStream);

    }
}
