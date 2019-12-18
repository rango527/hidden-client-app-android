package com.urbanairship.debug.push.persistence;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.DataSource.Factory;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PushDao_Impl implements PushDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PushEntity> __insertionAdapterOfPushEntity;

  private final SharedSQLiteStatement __preparedStmtOfTrimPushes;

  public PushDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPushEntity = new EntityInsertionAdapter<PushEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `pushes` (`id`,`pushId`,`payload`,`time`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PushEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getPushId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPushId());
        }
        if (value.getPayload() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPayload());
        }
        stmt.bindLong(4, value.getTime());
      }
    };
    this.__preparedStmtOfTrimPushes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM pushes where id NOT IN (SELECT id from pushes ORDER BY time LIMIT ?)";
        return _query;
      }
    };
  }

  @Override
  public void insertPush(final PushEntity push) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPushEntity.insert(push);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void trimPushes(final long count) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfTrimPushes.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, count);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfTrimPushes.release(_stmt);
    }
  }

  @Override
  public DataSource.Factory<Integer, PushEntity> getPushes() {
    final String _sql = "SELECT * FROM pushes ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new DataSource.Factory<Integer, PushEntity>() {
      @Override
      public LimitOffsetDataSource<PushEntity> create() {
        return new LimitOffsetDataSource<PushEntity>(__db, _statement, false , "pushes") {
          @Override
          protected List<PushEntity> convertRows(Cursor cursor) {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            final int _cursorIndexOfPushId = CursorUtil.getColumnIndexOrThrow(cursor, "pushId");
            final int _cursorIndexOfPayload = CursorUtil.getColumnIndexOrThrow(cursor, "payload");
            final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(cursor, "time");
            final List<PushEntity> _res = new ArrayList<PushEntity>(cursor.getCount());
            while(cursor.moveToNext()) {
              final PushEntity _item;
              final int _tmpId;
              _tmpId = cursor.getInt(_cursorIndexOfId);
              final String _tmpPushId;
              _tmpPushId = cursor.getString(_cursorIndexOfPushId);
              final String _tmpPayload;
              _tmpPayload = cursor.getString(_cursorIndexOfPayload);
              final long _tmpTime;
              _tmpTime = cursor.getLong(_cursorIndexOfTime);
              _item = new PushEntity(_tmpId,_tmpPushId,_tmpPayload,_tmpTime);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }

  @Override
  public LiveData<PushEntity> getPush(final String pushId) {
    final String _sql = "SELECT * FROM pushes WHERE pushId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (pushId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, pushId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"pushes"}, false, new Callable<PushEntity>() {
      @Override
      public PushEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPushId = CursorUtil.getColumnIndexOrThrow(_cursor, "pushId");
          final int _cursorIndexOfPayload = CursorUtil.getColumnIndexOrThrow(_cursor, "payload");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final PushEntity _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpPushId;
            _tmpPushId = _cursor.getString(_cursorIndexOfPushId);
            final String _tmpPayload;
            _tmpPayload = _cursor.getString(_cursorIndexOfPayload);
            final long _tmpTime;
            _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            _result = new PushEntity(_tmpId,_tmpPushId,_tmpPayload,_tmpTime);
          } else {
            _result = null;
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
}
