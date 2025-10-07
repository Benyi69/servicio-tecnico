package com.serviciotecnico.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ServiceDao_Impl implements ServiceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ServiceTicketEntity> __insertionAdapterOfServiceTicketEntity;

  public ServiceDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfServiceTicketEntity = new EntityInsertionAdapter<ServiceTicketEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `service_tickets` (`id`,`clienteNombre`,`telefono`,`tipoVehiculo`,`marca`,`modelo`,`patente`,`descripcion`,`fotoUri`,`estado`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ServiceTicketEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getClienteNombre() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getClienteNombre());
        }
        if (value.getTelefono() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTelefono());
        }
        if (value.getTipoVehiculo() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTipoVehiculo());
        }
        if (value.getMarca() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMarca());
        }
        if (value.getModelo() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getModelo());
        }
        if (value.getPatente() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPatente());
        }
        if (value.getDescripcion() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDescripcion());
        }
        if (value.getFotoUri() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getFotoUri());
        }
        if (value.getEstado() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getEstado());
        }
      }
    };
  }

  @Override
  public Object insert(final ServiceTicketEntity ticket,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfServiceTicketEntity.insertAndReturnId(ticket);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<ServiceTicketEntity>> getAllFlow() {
    final String _sql = "SELECT * FROM service_tickets ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"service_tickets"}, new Callable<List<ServiceTicketEntity>>() {
      @Override
      public List<ServiceTicketEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClienteNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "clienteNombre");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfTipoVehiculo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoVehiculo");
          final int _cursorIndexOfMarca = CursorUtil.getColumnIndexOrThrow(_cursor, "marca");
          final int _cursorIndexOfModelo = CursorUtil.getColumnIndexOrThrow(_cursor, "modelo");
          final int _cursorIndexOfPatente = CursorUtil.getColumnIndexOrThrow(_cursor, "patente");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfFotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fotoUri");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final List<ServiceTicketEntity> _result = new ArrayList<ServiceTicketEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ServiceTicketEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpClienteNombre;
            if (_cursor.isNull(_cursorIndexOfClienteNombre)) {
              _tmpClienteNombre = null;
            } else {
              _tmpClienteNombre = _cursor.getString(_cursorIndexOfClienteNombre);
            }
            final String _tmpTelefono;
            if (_cursor.isNull(_cursorIndexOfTelefono)) {
              _tmpTelefono = null;
            } else {
              _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            }
            final String _tmpTipoVehiculo;
            if (_cursor.isNull(_cursorIndexOfTipoVehiculo)) {
              _tmpTipoVehiculo = null;
            } else {
              _tmpTipoVehiculo = _cursor.getString(_cursorIndexOfTipoVehiculo);
            }
            final String _tmpMarca;
            if (_cursor.isNull(_cursorIndexOfMarca)) {
              _tmpMarca = null;
            } else {
              _tmpMarca = _cursor.getString(_cursorIndexOfMarca);
            }
            final String _tmpModelo;
            if (_cursor.isNull(_cursorIndexOfModelo)) {
              _tmpModelo = null;
            } else {
              _tmpModelo = _cursor.getString(_cursorIndexOfModelo);
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
            final String _tmpFotoUri;
            if (_cursor.isNull(_cursorIndexOfFotoUri)) {
              _tmpFotoUri = null;
            } else {
              _tmpFotoUri = _cursor.getString(_cursorIndexOfFotoUri);
            }
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            _item = new ServiceTicketEntity(_tmpId,_tmpClienteNombre,_tmpTelefono,_tmpTipoVehiculo,_tmpMarca,_tmpModelo,_tmpPatente,_tmpDescripcion,_tmpFotoUri,_tmpEstado);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id,
      final Continuation<? super ServiceTicketEntity> continuation) {
    final String _sql = "SELECT * FROM service_tickets WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ServiceTicketEntity>() {
      @Override
      public ServiceTicketEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfClienteNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "clienteNombre");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfTipoVehiculo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipoVehiculo");
          final int _cursorIndexOfMarca = CursorUtil.getColumnIndexOrThrow(_cursor, "marca");
          final int _cursorIndexOfModelo = CursorUtil.getColumnIndexOrThrow(_cursor, "modelo");
          final int _cursorIndexOfPatente = CursorUtil.getColumnIndexOrThrow(_cursor, "patente");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfFotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "fotoUri");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final ServiceTicketEntity _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpClienteNombre;
            if (_cursor.isNull(_cursorIndexOfClienteNombre)) {
              _tmpClienteNombre = null;
            } else {
              _tmpClienteNombre = _cursor.getString(_cursorIndexOfClienteNombre);
            }
            final String _tmpTelefono;
            if (_cursor.isNull(_cursorIndexOfTelefono)) {
              _tmpTelefono = null;
            } else {
              _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            }
            final String _tmpTipoVehiculo;
            if (_cursor.isNull(_cursorIndexOfTipoVehiculo)) {
              _tmpTipoVehiculo = null;
            } else {
              _tmpTipoVehiculo = _cursor.getString(_cursorIndexOfTipoVehiculo);
            }
            final String _tmpMarca;
            if (_cursor.isNull(_cursorIndexOfMarca)) {
              _tmpMarca = null;
            } else {
              _tmpMarca = _cursor.getString(_cursorIndexOfMarca);
            }
            final String _tmpModelo;
            if (_cursor.isNull(_cursorIndexOfModelo)) {
              _tmpModelo = null;
            } else {
              _tmpModelo = _cursor.getString(_cursorIndexOfModelo);
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
            final String _tmpFotoUri;
            if (_cursor.isNull(_cursorIndexOfFotoUri)) {
              _tmpFotoUri = null;
            } else {
              _tmpFotoUri = _cursor.getString(_cursorIndexOfFotoUri);
            }
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            _result = new ServiceTicketEntity(_tmpId,_tmpClienteNombre,_tmpTelefono,_tmpTipoVehiculo,_tmpMarca,_tmpModelo,_tmpPatente,_tmpDescripcion,_tmpFotoUri,_tmpEstado);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
