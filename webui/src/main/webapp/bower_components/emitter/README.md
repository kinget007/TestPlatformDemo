# Emitter
[![npm](https://img.shields.io/npm/v/async-emitter.svg?style=flat-square)](https://npmjs.com/package/async-emitter)
[![npm license](https://img.shields.io/npm/l/async-emitter.svg?style=flat-square)](https://npmjs.com/package/async-emitter)
[![npm downloads](https://img.shields.io/npm/dm/async-emitter.svg?style=flat-square)](https://npmjs.com/package/async-emitter)
[![build status](https://img.shields.io/travis/jhermsmeier/emitter.js.svg?style=flat-square)](https://travis-ci.org/jhermsmeier/emitter.js)

Non-blocking event emitter

## Install via [npm](https://npmjs.com/package/async-emitter)

```sh
$ npm install --save async-emitter
```

## API

### Class Properties

- *function* __Emitter.nextTick__
- *boolean* __Emitter.warn__

### Instance Methods

- {Emitter} __emitter.on__( *string* __event__, *function|object* __handler__ )
- {Emitter} __emitter.once__( *string* __event__, *function|object* __handler__ )
- {Boolean} __emitter.emit__( *string* __event__, [arg1], [arg2], [...] )
- {Boolean} __emitter.emitSync__( *string* __event__, [arg1], [arg2], [...] )
- {Emitter} __emitter.removeListener__( *string* __event__, *function|object* __handler__ )
- {Emitter} __emitter.removeAllListeners__( *string* [event] )
- {Emitter} __emitter.setMaxListeners__( *number* __value__ )
- {Array} __emitter.listeners__( *string* __event__ )
