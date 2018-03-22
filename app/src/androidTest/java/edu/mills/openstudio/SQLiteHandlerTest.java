package edu.mills.openstudio;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Regina on 5/8/2017.
 */
public class SQLiteHandlerTest {
    private static final String USER1_NAME = "New User";
    private static final String USER1_EMAIL = "user@newuser.com";
    private static final String USER1_UID = "5910de93bd1196.21217466";
    private static final String USER1_CREATEDAT = "2017-05-08 21:09:39";
    private SQLiteHandler db;

    @Before
    public void setUp() throws Exception {
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteHandler(context);
        db.addUser(USER1_NAME, USER1_EMAIL, USER1_UID, USER1_CREATEDAT);
    }
    @After
    public void takeDown() {
        db.deleteUsers();
    }
    @Test
    public void getUserDetails() throws Exception {
        assertEquals("New User", db.getUserDetails().get("name"));
        assertEquals("user@newuser.com", db.getUserDetails().get("email"));
        assertEquals("5910de93bd1196.21217466", db.getUserDetails().get("uid"));
        assertEquals("2017-05-08 21:09:39", db.getUserDetails().get("created_at"));
    }

}