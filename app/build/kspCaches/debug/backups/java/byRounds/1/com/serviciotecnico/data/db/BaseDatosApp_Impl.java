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
public final class BaseDatosApp_Impl extends BaseDatosApp {
  private volatile ServiceDao _serviceDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ordenes_servicio` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cliente` TEXT NOT NULL, `vehiculo` TEXT NOT NULL, `patente` TEXT NOT NULL, `descripcion` TEXT NOT NULL, `completado` INTEGER NOT NULL, `imagenUri` TEXT, `fechaRegistro` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `arreglos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `descripcion` TEXT NOT NULL, `precio` REAL NOT NULL, `ticketId` INTEGER NOT NULL, FOREIGN KEY(`ticketId`) REFERENCES `ordenes_servicio`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_arreglos_ticketId` ON `arreglos` (`ticketId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '25cf07ee54667bc920c09ac8fce7b096')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ordenes_servicio`");
        _db.execSQL("DROP TABLE IF EXISTS `arreglos`");
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
        _db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsOrdenesServicio = new HashMap<String, TableInfo.Column>(8);
        _columnsOrdenesServicio.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("cliente", new TableInfo.Column("cliente", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("vehiculo", new TableInfo.Column("vehiculo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("patente", new TableInfo.Column("patente", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("descripcion", new TableInfo.Column("descripcion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("completado", new TableInfo.Column("completado", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("imagenUri", new TableInfo.Column("imagenUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrdenesServicio.put("fechaRegistro", new TableInfo.Column("fechaRegistro", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrdenesServicio = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrdenesServicio = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrdenesServicio = new TableInfo("ordenes_servicio", _columnsOrdenesServicio, _foreignKeysOrdenesServicio, _indicesOrdenesServicio);
        final TableInfo _existingOrdenesServicio = TableInfo.read(_db, "ordenes_servicio");
        if (! _infoOrdenesServicio.equals(_existingOrdenesServicio)) {
          return new RoomOpenHelper.ValidationResult(false, "ordenes_servicio(com.serviciotecnico.data.db.ServiceTicketEntity).\n"
                  + " Expected:\n" + _infoOrdenesServicio + "\n"
                  + " Found:\n" + _existingOrdenesServicio);
        }
        final HashMap<String, TableInfo.Column> _columnsArreglos = new HashMap<String, TableInfo.Column>(4);
        _columnsArreglos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArreglos.put("descripcion", new TableInfo.Column("descripcion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArreglos.put("precio", new TableInfo.Column("precio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArreglos.put("ticketId", new TableInfo.Column("ticketId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArreglos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysArreglos.add(new TableInfo.ForeignKey("ordenes_servicio", "CASCADE", "NO ACTION",Arrays.asList("ticketId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesArreglos = new HashSet<TableInfo.Index>(1);
        _indicesArreglos.add(new TableInfo.Index("index_arreglos_ticketId", false, Arrays.asList("ticketId"), Arrays.asList("ASC")));
        final TableInfo _infoArreglos = new TableInfo("arreglos", _columnsArreglos, _foreignKeysArreglos, _indicesArreglos);
        final TableInfo _existingArreglos = TableInfo.read(_db, "arreglos");
        if (! _infoArreglos.equals(_existingArreglos)) {
          return new RoomOpenHelper.ValidationResult(false, "arreglos(com.serviciotecnico.data.db.ArregloEntity).\n"
                  + " Expected:\n" + _infoArreglos + "\n"
                  + " Found:\n" + _existingArreglos);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "25cf07ee54667bc920c09ac8fce7b096", "c51931334d4aec514d7a7159161d9ac0");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "ordenes_servicio","arreglos");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `ordenes_servicio`");
      _db.execSQL("DELETE FROM `arreglos`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
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
