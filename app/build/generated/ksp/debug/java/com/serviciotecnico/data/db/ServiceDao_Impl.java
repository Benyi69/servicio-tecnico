package com.serviciotecnico.data.db;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ServiceDao_Impl implements ServiceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ServiceTicketEntity> __insertionAdapterOfServiceTicketEntity;

  private final EntityInsertionAdapter<ArregloEntity> __insertionAdapterOfArregloEntity;

  private final EntityDeletionOrUpdateAdapter<ServiceTicketEntity> __deletionAdapterOfServiceTicketEntity;

  private final EntityDeletionOrUpdateAdapter<ArregloEntity> __deletionAdapterOfArregloEntity;

  private final EntityDeletionOrUpdateAdapter<ServiceTicketEntity> __updateAdapterOfServiceTicketEntity;

  private final SharedSQLiteStatement __preparedStmtOfEliminarTodos;

  public ServiceDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfServiceTicketEntity = new EntityInsertionAdapter<ServiceTicketEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ordenes_servicio` (`id`,`cliente`,`vehiculo`,`patente`,`descripcion`,`completado`,`imagenUri`,`fechaRegistro`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServiceTicketEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getCliente() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCliente());
        }
        if (value.getVehiculo() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVehiculo());
        }
        if (value.getPatente() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPatente());
        }
        if (value.getDescripcion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescripcion());
        }
        final int _tmp = value.getCompletado() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        if (value.getImagenUri() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImagenUri());
        }
        stmt.bindLong(8, value.getFechaRegistro());
      }
    };
    this.__insertionAdapterOfArregloEntity = new EntityInsertionAdapter<ArregloEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `arreglos` (`id`,`descripcion`,`precio`,`ticketId`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ArregloEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getDescripcion() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDescripcion());
        }
        stmt.bindDouble(3, value.getPrecio());
        stmt.bindLong(4, value.getTicketId());
      }
    };
    this.__deletionAdapterOfServiceTicketEntity = new EntityDeletionOrUpdateAdapter<ServiceTicketEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ordenes_servicio` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServiceTicketEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__deletionAdapterOfArregloEntity = new EntityDeletionOrUpdateAdapter<ArregloEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `arreglos` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ArregloEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfServiceTicketEntity = new EntityDeletionOrUpdateAdapter<ServiceTicketEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ordenes_servicio` SET `id` = ?,`cliente` = ?,`vehiculo` = ?,`patente` = ?,`descripcion` = ?,`completado` = ?,`imagenUri` = ?,`fechaRegistro` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServiceTicketEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getCliente() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCliente());
        }
        if (value.getVehiculo() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVehiculo());
        }
        if (value.getPatente() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPatente());
        }
        if (value.getDescripcion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescripcion());
        }
        final int _tmp = value.getCompletado() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        if (value.getImagenUri() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImagenUri());
        }
        stmt.bindLong(8, value.getFechaRegistro());
        stmt.bindLong(9, value.getId());
      }
    };
    this.__preparedStmtOfEliminarTodos = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ordenes_servicio";
        return _query;
      }
    };
  }

  @Override
  public Object insertar(final ServiceTicketEntity entidad,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfServiceTicketEntity.insertAndReturnId(entidad);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertarArreglo(final ArregloEntity arreglo,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfArregloEntity.insertAndReturnId(arreglo);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object eliminar(final ServiceTicketEntity entidad,
      final Continuation<? super Integer> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total +=__deletionAdapterOfServiceTicketEntity.handle(entidad);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object eliminarArreglo(final ArregloEntity arreglo,
      final Continuation<? super Integer> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total +=__deletionAdapterOfArregloEntity.handle(arreglo);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object actualizar(final ServiceTicketEntity entidad,
      final Continuation<? super Integer> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        int _total = 0;
        __db.beginTransaction();
        try {
          _total +=__updateAdapterOfServiceTicketEntity.handle(entidad);
          __db.setTransactionSuccessful();
          return _total;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object eliminarTodos(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEliminarTodos.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfEliminarTodos.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<TicketConArreglos>> obtenerTodos() {
    final String _sql = "SELECT * FROM ordenes_servicio ORDER BY fechaRegistro DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"arreglos","ordenes_servicio"}, new Callable<List<TicketConArreglos>>() {
      @Override
      public List<TicketConArreglos> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "cliente");
            final int _cursorIndexOfVehiculo = CursorUtil.getColumnIndexOrThrow(_cursor, "vehiculo");
            final int _cursorIndexOfPatente = CursorUtil.getColumnIndexOrThrow(_cursor, "patente");
            final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
            final int _cursorIndexOfCompletado = CursorUtil.getColumnIndexOrThrow(_cursor, "completado");
            final int _cursorIndexOfImagenUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagenUri");
            final int _cursorIndexOfFechaRegistro = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaRegistro");
            final LongSparseArray<ArrayList<ArregloEntity>> _collectionArreglos = new LongSparseArray<ArrayList<ArregloEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
              ArrayList<ArregloEntity> _tmpArreglosCollection = _collectionArreglos.get(_tmpKey);
              if (_tmpArreglosCollection == null) {
                _tmpArreglosCollection = new ArrayList<ArregloEntity>();
                _collectionArreglos.put(_tmpKey, _tmpArreglosCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiparreglosAscomServiciotecnicoDataDbArregloEntity(_collectionArreglos);
            final List<TicketConArreglos> _result = new ArrayList<TicketConArreglos>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final TicketConArreglos _item;
              final ServiceTicketEntity _tmpTicket;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpCliente;
              if (_cursor.isNull(_cursorIndexOfCliente)) {
                _tmpCliente = null;
              } else {
                _tmpCliente = _cursor.getString(_cursorIndexOfCliente);
              }
              final String _tmpVehiculo;
              if (_cursor.isNull(_cursorIndexOfVehiculo)) {
                _tmpVehiculo = null;
              } else {
                _tmpVehiculo = _cursor.getString(_cursorIndexOfVehiculo);
              }
              final String _tmpPatente;
              if (_cursor.isNull(_cursorIndexOfPatente)) {
                _tmpPatente = null;
              } else {
                _tmpPatente = _cursor.getString(_cursorIndexOfPatente);
              }
              final String _tmpDescripcion;
              if (_cursor.isNull(_cursorIndexOfDescripcion)) {
                _tmpDescripcion = null;
              } else {
                _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
              }
              final boolean _tmpCompletado;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfCompletado);
              _tmpCompletado = _tmp != 0;
              final String _tmpImagenUri;
              if (_cursor.isNull(_cursorIndexOfImagenUri)) {
                _tmpImagenUri = null;
              } else {
                _tmpImagenUri = _cursor.getString(_cursorIndexOfImagenUri);
              }
              final long _tmpFechaRegistro;
              _tmpFechaRegistro = _cursor.getLong(_cursorIndexOfFechaRegistro);
              _tmpTicket = new ServiceTicketEntity(_tmpId,_tmpCliente,_tmpVehiculo,_tmpPatente,_tmpDescripcion,_tmpCompletado,_tmpImagenUri,_tmpFechaRegistro);
              ArrayList<ArregloEntity> _tmpArreglosCollection_1 = null;
              final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpArreglosCollection_1 = _collectionArreglos.get(_tmpKey_1);
              if (_tmpArreglosCollection_1 == null) {
                _tmpArreglosCollection_1 = new ArrayList<ArregloEntity>();
              }
              _item = new TicketConArreglos(_tmpTicket,_tmpArreglosCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<TicketConArreglos> obtenerPorId(final long id) {
    final String _sql = "SELECT * FROM ordenes_servicio WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"arreglos","ordenes_servicio"}, new Callable<TicketConArreglos>() {
      @Override
      public TicketConArreglos call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "cliente");
            final int _cursorIndexOfVehiculo = CursorUtil.getColumnIndexOrThrow(_cursor, "vehiculo");
            final int _cursorIndexOfPatente = CursorUtil.getColumnIndexOrThrow(_cursor, "patente");
            final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
            final int _cursorIndexOfCompletado = CursorUtil.getColumnIndexOrThrow(_cursor, "completado");
            final int _cursorIndexOfImagenUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imagenUri");
            final int _cursorIndexOfFechaRegistro = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaRegistro");
            final LongSparseArray<ArrayList<ArregloEntity>> _collectionArreglos = new LongSparseArray<ArrayList<ArregloEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
              ArrayList<ArregloEntity> _tmpArreglosCollection = _collectionArreglos.get(_tmpKey);
              if (_tmpArreglosCollection == null) {
                _tmpArreglosCollection = new ArrayList<ArregloEntity>();
                _collectionArreglos.put(_tmpKey, _tmpArreglosCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiparreglosAscomServiciotecnicoDataDbArregloEntity(_collectionArreglos);
            final TicketConArreglos _result;
            if(_cursor.moveToFirst()) {
              final ServiceTicketEntity _tmpTicket;
              final long _tmpId;
              _tmpId = _cursor.getLong(_cursorIndexOfId);
              final String _tmpCliente;
              if (_cursor.isNull(_cursorIndexOfCliente)) {
                _tmpCliente = null;
              } else {
                _tmpCliente = _cursor.getString(_cursorIndexOfCliente);
              }
              final String _tmpVehiculo;
              if (_cursor.isNull(_cursorIndexOfVehiculo)) {
                _tmpVehiculo = null;
              } else {
                _tmpVehiculo = _cursor.getString(_cursorIndexOfVehiculo);
              }
              final String _tmpPatente;
              if (_cursor.isNull(_cursorIndexOfPatente)) {
                _tmpPatente = null;
              } else {
                _tmpPatente = _cursor.getString(_cursorIndexOfPatente);
              }
              final String _tmpDescripcion;
              if (_cursor.isNull(_cursorIndexOfDescripcion)) {
                _tmpDescripcion = null;
              } else {
                _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
              }
              final boolean _tmpCompletado;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfCompletado);
              _tmpCompletado = _tmp != 0;
              final String _tmpImagenUri;
              if (_cursor.isNull(_cursorIndexOfImagenUri)) {
                _tmpImagenUri = null;
              } else {
                _tmpImagenUri = _cursor.getString(_cursorIndexOfImagenUri);
              }
              final long _tmpFechaRegistro;
              _tmpFechaRegistro = _cursor.getLong(_cursorIndexOfFechaRegistro);
              _tmpTicket = new ServiceTicketEntity(_tmpId,_tmpCliente,_tmpVehiculo,_tmpPatente,_tmpDescripcion,_tmpCompletado,_tmpImagenUri,_tmpFechaRegistro);
              ArrayList<ArregloEntity> _tmpArreglosCollection_1 = null;
              final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpArreglosCollection_1 = _collectionArreglos.get(_tmpKey_1);
              if (_tmpArreglosCollection_1 == null) {
                _tmpArreglosCollection_1 = new ArrayList<ArregloEntity>();
              }
              _result = new TicketConArreglos(_tmpTicket,_tmpArreglosCollection_1);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshiparreglosAscomServiciotecnicoDataDbArregloEntity(
      final LongSparseArray<ArrayList<ArregloEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<ArregloEntity>> _tmpInnerMap = new LongSparseArray<ArrayList<ArregloEntity>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiparreglosAscomServiciotecnicoDataDbArregloEntity(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<ArregloEntity>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiparreglosAscomServiciotecnicoDataDbArregloEntity(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`descripcion`,`precio`,`ticketId` FROM `arreglos` WHERE `ticketId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "ticketId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfDescripcion = 1;
      final int _cursorIndexOfPrecio = 2;
      final int _cursorIndexOfTicketId = 3;
      while(_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_itemKeyIndex);
        ArrayList<ArregloEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final ArregloEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final String _tmpDescripcion;
          if (_cursor.isNull(_cursorIndexOfDescripcion)) {
            _tmpDescripcion = null;
          } else {
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
          }
          final double _tmpPrecio;
          _tmpPrecio = _cursor.getDouble(_cursorIndexOfPrecio);
          final long _tmpTicketId;
          _tmpTicketId = _cursor.getLong(_cursorIndexOfTicketId);
          _item_1 = new ArregloEntity(_tmpId,_tmpDescripcion,_tmpPrecio,_tmpTicketId);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
