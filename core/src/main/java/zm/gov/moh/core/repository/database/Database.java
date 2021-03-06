package zm.gov.moh.core.repository.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import zm.gov.moh.core.repository.database.dao.derived.CervicalCancerDao;
import zm.gov.moh.core.repository.database.dao.derived.ClientDao;
import zm.gov.moh.core.repository.database.dao.derived.FacilityDistrictCodeDao;
import zm.gov.moh.core.repository.database.dao.derived.ProviderUserDao;
import zm.gov.moh.core.repository.database.dao.derived.VitalsDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationTagDao;
import zm.gov.moh.core.repository.database.dao.domain.LocationTagMapDao;
import zm.gov.moh.core.repository.database.dao.domain.ObsDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientIdentifierDao;
import zm.gov.moh.core.repository.database.dao.domain.PatientIdentifierTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAddressDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonAttributeTypeDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonDao;
import zm.gov.moh.core.repository.database.dao.domain.PersonNameDao;
import zm.gov.moh.core.repository.database.dao.domain.ProviderDao;
import zm.gov.moh.core.repository.database.dao.domain.UserDao;
import zm.gov.moh.core.repository.database.entity.derived.ProviderUser;
import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttribute;
import zm.gov.moh.core.repository.database.entity.domain.LocationAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.LocationTag;
import zm.gov.moh.core.repository.database.entity.domain.LocationTagMap;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Patient;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifier;
import zm.gov.moh.core.repository.database.entity.domain.PatientIdentifierType;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonAddress;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttribute;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeType;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;
import zm.gov.moh.core.repository.database.entity.domain.User;

@android.arch.persistence.room.Database(
        entities = {

                Person.class,
                PersonAddress.class,
                PersonAttribute.class,
                PersonAttributeType.class,
                PersonName.class,
                Patient.class,
                PatientIdentifier.class,
                PatientIdentifierType.class,
                Location.class,
                LocationTag.class,
                LocationTagMap.class,
                LocationAttribute.class,
                LocationAttributeType.class,
                Provider.class,
                User.class,
                Obs.class
        }, version = 2)
@TypeConverters(Converter.class)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "mcare";

    private static volatile Database dbInstance;

    //Domain
    public abstract PersonDao personDao();
    public abstract PersonAddressDao personAddressDao();
    public abstract PersonAttributeDao personAttributeDao();
    public abstract PersonAttributeTypeDao personAttributeTypeDao();
    public abstract PersonNameDao personNameDao();
    public abstract LocationDao locationDao();
    public abstract LocationAttributeDao locationAttributeDao();
    public abstract LocationTagDao locationTagDao();
    public abstract LocationTagMapDao locationTagMapDao();
    public abstract LocationAttributeTypeDao locationAttributeTypeDao();
    public abstract ProviderUserDao providerUserDao();
    public abstract UserDao userDao();
    public abstract ProviderDao providerDao();
    public abstract PatientDao patientDao();
    public abstract PatientIdentifierDao patientIdentifierDao();
    public abstract PatientIdentifierTypeDao patientIdentifierTypeDao();
    public abstract ObsDao obsDao();


    //Derived
    public abstract ClientDao clientDao();
    public abstract VitalsDao vitalsDao();
    public abstract FacilityDistrictCodeDao facilityDistrictCodeDao();
    public abstract CervicalCancerDao cervicalCancerDao();

    //database getter
    public static Database getDatabase(final Context context){

        if (dbInstance == null)
              synchronized (Database.class){
                  if (dbInstance == null)
                     dbInstance = Room.databaseBuilder(context.getApplicationContext(),Database.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
              }

         return dbInstance;
    }
}

/**
 *
 *package com.example.zita.architecturecomponent.repository.database;
 *
 * import android.arch.persistence.db.SupportSQLiteDatabase;
 * import android.arch.persistence.room.Database;
 * import android.arch.persistence.room.Room;
 * import android.arch.persistence.room.RoomDatabase;
 * import android.content.Context;
 * import android.os.AsyncTask;
 * import android.support.annotation.NonNull;
 *
 * import com.example.zita.architecturecomponent.repository.database.doa.PersonDoa;
 * import com.example.zita.architecturecomponent.repository.database.entity.Person;
 *
 * @Database(entities = {Person.class}, version = 1)
 * public abstract class AppDB extends RoomDatabase {
 *
 *     static volatile AppDB dbInstance;
 *
 *     public abstract PersonDoa personDoa();
 *
 *     private static RoomDatabase.Callback dbCallback = new Callback() {
 *         @Override
 *         public void onOpen(@NonNull SupportSQLiteDatabase db) {
 *             super.onOpen(db);
 *
 *             new PopulateDb(dbInstance).execute();
 *
 *         }
 *     };
 *
 *     public static AppDB getDB(final Context context){
 *         if (dbInstance == null)
 *             synchronized (AppDB.class){
 *                 if (dbInstance == null){
 *                     dbInstance = Room.databaseBuilder(context.getApplicationContext(),AppDB.class,"appdb").build();
 *                 }
 *             }
 *         return dbInstance;
 *     }
 *
 *
 *
 *     private static class PopulateDb extends AsyncTask<Void,Void,Void>{
 *
 *         PersonDoa mPersonDoa;
 *         PopulateDb(AppDB database){
 *             mPersonDoa = database.personDoa();
 *         }
 *
 *         @Override
 *         protected Void doInBackground(Void... voids) {
 *
 *             Person person1 = new Person("Zita","Tembo","Dev");
 *             Person person2 = new Person("Tony","Tembo","DevOps");
 *             mPersonDoa.insert(person1);
 *             mPersonDoa.insert(person2);
 *
 *             return null;
 *         }
 *     }
 * }
 */
