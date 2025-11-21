package com.serviciotecnico.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ServiceDao _serviceDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `service_tickets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clienteNombre` TEXT NOT NULL, `telefono` TEXT NOT NULL, `tipoVehiculo` TEXT NOT NULL, `marca` TEXT NOT NULL, `modelo` TEXT NOT NULL, `patente` TEXT NOT NULL, `descripcion` TEXT NOT NULL, `fotoUri` TEXT, `estado` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '79da23f65a525341f90e27966c2141d7')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `service_tickets`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsServiceTickets = new HashMap<String, TableInfo.Column>(10);
        _columnsServiceTickets.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("clienteNombre", new TableInfo.Column("clienteNombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("telefono", new TableInfo.Column("telefono", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("tipoVehiculo", new TableInfo.Column("tipoVehiculo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("marca", new TableInfo.Column("marca", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("modelo", new TableInfo.Column("modelo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("patente", new TableInfo.Column("patente", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("descripcion", new TableInfo.Column("descripcion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("fotoUri", new TableInfo.Column("fotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceTickets.put("estado", new TableInfo.Column("estado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysServiceTickets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesServiceTickets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoServiceTickets = new TableInfo("service_tickets", _columnsServiceTickets, _foreignKeysServiceTickets, _indicesServiceTickets);
        final TableInfo _existingServiceTickets = TableInfo.read(_db, "service_tickets");
        if (! _infoServiceTickets.equals(_existingServiceTickets)) {
          return new RoomOpenHelper.ValidationResult(false, "service_tickets(com.serviciotecnico.data.db.ServiceTicketEntity).\n"
                  + " Expected:\n" + _infoServiceTickets + "\n"
                  + " Found:\n" + _existingServiceTickets);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "79da23f65a525341f90e27966c2141d7", "d8707af4b192d3a978aee4653de9fceb");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "service_tickets");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `service_tickets`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ServiceDao.class, ServiceDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public ServiceDao serviceDao() {
    if (_serviceDao != null) {
      return _serviceDao;
    } else {
      synchronized(this) {
        if(_serviceDao == null) {
          _serviceDao = new ServiceDao_Impl(this);
        }
        return _serviceDao;
      }
    }
  }
}
