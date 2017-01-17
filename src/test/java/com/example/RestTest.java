package com.example;

        import static com.jayway.restassured.RestAssured.get;

        import java.io.InputStream;
        import java.util.Properties;
        import org.json.JSONArray;
        import org.json.JSONException;
        import com.jayway.restassured.response.Response;
        import org.junit.Assert;
        import org.junit.Test;

public class RestTest {

    public static Properties propertiesLoader(String fileName) {
        Properties prop = null;
        try (InputStream inputStream = RestTest.class.getClassLoader().getResourceAsStream(fileName)) {
            prop = new Properties();
            prop.load(inputStream);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            Assert.fail("File does not exists");
        }
        return prop;
    }

    @Test
    public void getRequestFindaltSpellings() throws JSONException {
        Properties prop = propertiesLoader("test.properties");
        String elements = prop.getProperty("elements");
        Assert.assertNotNull(elements);

        Response resp = get("http://restcountries.eu/rest/v1/name/norway");
        JSONArray jsonResponse = new JSONArray(resp.asString());
        String altSpellings = String.valueOf(jsonResponse.getJSONObject(0).getJSONArray("altSpellings"));
        Assert.assertEquals(elements, altSpellings);
    }

    @Test
    public void getRequestFindCapital() throws JSONException {
        Response resp = get("http://restcountries.eu/rest/v1/name/norway");
        JSONArray jsonResponse = new JSONArray(resp.asString());
        String capital = jsonResponse.getJSONObject(0).getString("capital");
        Assert.assertEquals(capital, "Oslo");
    }
}