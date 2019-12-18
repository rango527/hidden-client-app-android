package com.urbanairship.debug.event.persistence;

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
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EventDao_Impl implements EventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EventEntity> __insertionAdapterOfEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfTrimEvents;

  private final SharedSQLiteStatement __preparedStmtOfTrimOldEvents;

  public EventDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEventEntity = new EntityInsertionAdapter<EventEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `events` (`id`,`eventId`,`session`,`payload`,`time`,`type`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EventEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getEventId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEventId());
        }
        if (value.getSession() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSession());
        }
        if (value.getPayload() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPayload());
        }
        stmt.bindLong(5, value.getTime());
        if (value.getType() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getType());
        }
      }
    };
    this.__preparedStmtOfTrimEvents = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM events where eventId NOT IN (SELECT eventId from events ORDER BY time LIMIT ?)";
        return _query;
      }
    };
    this.__preparedStmtOfTrimOldEvents = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM events WHERE datetime( time / 1000 , 'unixepoch') < datetime('now', '-' || ? || ' day')";
        return _query;
      }
    };
  }

  @Override
  public void insertEvent(final EventEntity event) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEventEntity.insert(event);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void trimEvents(final long count) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfTrimEvents.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, count);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfTrimEvents.release(_stmt);
    }
  }

  @Override
  public void trimOldEvents(final int days) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfTrimOldEvents.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, days);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfTrimOldEvents.release(_stmt);
    }
  }

  @Override
  public DataSource.Factory<Integer, EventEntity> getEvents() {
    final String _sql = "SELECT * FROM events ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new DataSource.Factory<Integer, EventEntity>() {
      @Override
      public LimitOffsetDataSource<EventEntity> create() {
        return new LimitOffsetDataSource<EventEntity>(__db, _statement, false , "events") {
          @Override
          protected List<EventEntity> convertRows(Cursor cursor) {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(cursor, "eventId");
            final int _cursorIndexOfSession = CursorUtil.getColumnIndexOrThrow(cursor, "session");
            final int _cursorIndexOfPayload = CursorUtil.getColumnIndexOrThrow(cursor, "payload");
            final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(cursor, "time");
            final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(cursor, "type");
            final List<EventEntity> _res = new ArrayList<EventEntity>(cursor.getCount());
            while(cursor.moveToNext()) {
              final EventEntity _item;
              final int _tmpId;
              _tmpId = cursor.getInt(_cursorIndexOfId);
              final String _tmpEventId;
              _tmpEventId = cursor.getString(_cursorIndexOfEventId);
              final String _tmpSession;
              _tmpSession = cursor.getString(_cursorIndexOfSession);
              final String _tmpPayload;
              _tmpPayload = cursor.getString(_cursorIndexOfPayload);
              final long _tmpTime;
              _tmpTime = cursor.getLong(_cursorIndexOfTime);
              final String _tmpType;
              _tmpType = cursor.getString(_cursorIndexOfType);
              _item = new EventEntity(_tmpId,_tmpEventId,_tmpSession,_tmpPayload,_tmpTime,_tmpType);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }

  @Override
  public DataSource.Factory<Integer, EventEntity> getEvents(final List<String> types) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT ");
    _stringBuilder.append("*");
    _stringBuilder.append(" FROM events WHERE type IN(");
    final int _inputSize = types.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY id DESC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : types) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    return new DataSource.Factory<Integer, EventEntity>() {
      @Override
      public LimitOffsetDataSource<EventEntity> create() {
        return new LimitOffsetDataSource<EventEntity>(__db, _statement, false , "events") {
          @Override
          protected List<EventEntity> convertRows(Cursor cursor) {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(cursor, "eventId");
            final int _cursorIndexOfSession = CursorUtil.getColumnIndexOrThrow(cursor, "session");
            final int _cursorIndexOfPayload = CursorUtil.getColumnIndexOrThrow(cursor, "payload");
            final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(cursor, "time");
            final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(cursor, "type");
            final List<EventEntity> _res = new ArrayList<EventEntity>(cursor.getCount());
            while(cursor.moveToNext()) {
              final EventEntity _item_1;
              final int _tmpId;
              _tmpId = cursor.getInt(_cursorIndexOfId);
              final String _tmpEventId;
              _tmpEventId = cursor.getString(_cursorIndexOfEventId);
              final String _tmpSession;
              _tmpSession = cursor.getString(_cursorIndexOfSession);
              final String _tmpPayload;
              _tmpPayload = cursor.getString(_cursorIndexOfPayload);
              final long _tmpTime;
              _tmpTime = cursor.getLong(_cursorIndexOfTime);
              final String _tmpType;
              _tmpType = cursor.getString(_cursorIndexOfType);
              _item_1 = new EventEntity(_tmpId,_tmpEventId,_tmpSession,_tmpPayload,_tmpTime,_tmpType);
              _res.add(_item_1);
            }
            return _res;
          }
        };
      }
    };
  }

  @Override
  public LiveData<EventEntity> getEvent(final String eventId) {
    final String _sql = "select * from events where eventId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (eventId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"events"}, false, new Callable<EventEntity>() {
      @Override
      public EventEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
          final int _cursorIndexOfSession = CursorUtil.getColumnIndexOrThrow(_cursor, "session");
          final int _cursorIndexOfPayload = CursorUtil.getColumnIndexOrThrow(_cursor, "payload");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final EventEntity _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpEventId;
            _tmpEventId = _cursor.getString(_cursorIndexOfEventId);
            final String _tmpSession;
            _tmpSession = _cursor.getString(_cursorIndexOfSession);
            final String _tmpPayload;
            _tmpPayload = _cursor.getString(_cursorIndexOfPayload);
            final long _tmpTime;
            _tmpTime = _cursor.getLong(_cursorIndexOfTime);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _result = new EventEntity(_tmpId,_tmpEventId,_tmpSession,_tmpPayload,_tmpTime,_tmpType);
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
