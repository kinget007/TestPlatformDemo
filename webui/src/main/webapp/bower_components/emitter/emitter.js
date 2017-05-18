void (function() {

  var global = this

  /**
   * Emitter constructor
   */
  function Emitter() {
    this._events = this._events || {}
    this._maxListeners = 10
    this._memLeakDetected = false
  }

  // Exports
  if( typeof module !== 'undefined' )
    module.exports = Emitter
  else
    global.Emitter = Emitter

  // Use when maxTickDepth is reached
  Emitter.immediate = ( typeof setImmediate === 'function' ) ?
    setImmediate.bind( global ) :
    setTimeout.bind( global )

  // Use until maxTickDepth is reached
  Emitter.tick = ( global.process && typeof process.nextTick === 'function' ) ?
    process.nextTick.bind( process ) :
    setTimeout.bind( global )

  // Shim maxTickDepth in the browser
  Emitter.maxTicks = global.process && process.maxTickDepth ?
    global.process.maxTickDepth : 1000

  // Keep track of tick count
  Emitter.tickCount = 0

  /**
   * Support for setTimeout(), nextTick() and setImmediate(),
   * with some added logic to prevent starving the event loop.
   * @type {Function}
   */
  Emitter.nextTick = function( fn ) {
    if( Emitter.tickCount++ >= Emitter.maxTicks ) {
      Emitter.tickCount = 0
      Emitter.immediate( fn )
    } else {
      Emitter.tick( fn )
    }
  }

  /**
   * Determines if Emitters warn
   * about potential memory leaks
   * @type {Boolean}
   */
  Emitter.warn = true

  /**
   * Emitter prototype
   * @type {Object}
   */
  Emitter.prototype = {

    constructor: Emitter,

    /**
     * Adds a listener for the specified event
     * @param  {String}   type
     * @param  {Function} handler
     * @return {Emitter}
     */
    on: function( type, handler ) {

      if( handler === void 0 || handler === null )
        throw new Error( 'Missing argument "handler"' )

      if( typeof handler !== 'function' && typeof handler.handleEvent !== 'function' )
        throw new TypeError( 'Handler must be a function.' )

      this._events[ type ] ?
        this._events[ type ].push( handler ) :
        this._events[ type ] = [ handler ]

      if( Emitter.warn && this._events[ type ].length > this._maxListeners ) {
        if( this._maxListeners > 0 && !this._memLeakDetected ) {
          this._memLeakDetected = true
          console.warn(
            'WARNING: Possible event emitter memory leak detected.',
            this._events[ type ].length, 'event handlers added.',
            'Use emitter.setMaxListeners() to increase the threshold.'
          )
          console.trace()
        }
      }

      return this

    },

    /**
     * Adds a one time listener for the specified event
     * @param  {String}   type
     * @param  {Function} handler
     * @return {Emitter}
     */
    once: function( type, handler ) {

      if( handler === void 0 || handler === null )
        throw new Error( 'Missing argument "handler"' )

      if( typeof handler !== 'function' && typeof handler.handleEvent !== 'function' )
        throw new TypeError( 'Handler must be a function.' )

      function wrapper() {
        this.removeListener( type, wrapper )
        typeof handler !== 'function'
          ? handler.handleEvent.apply( handler, arguments )
          : handler.apply( this, arguments )
      }

      this._events[ type ] ?
        this._events[ type ].push( wrapper ) :
        this._events[ type ] = [ wrapper ]

      return this

    },

    /**
     * Execute each of the listeners in order
     * with the supplied arguments
     * @param  {String}  type
     * @return {Boolean}
     */
    emit: function( type ) {

      var emitter = this
      var listeners = this._events[ type ]

      if( type === 'error' && !listeners ) {
        if( !this._events.error ) {
          throw !( arguments[1] instanceof Error ) ?
            new Error( 'Unhandled "error" event.' ) :
            arguments[1]
        }
      } else if( !listeners ) {
        return false
      }

      var argv = [].slice.call( arguments, 1 )
      var i, len = listeners.length

      function fire( handler, argv ) {
        typeof handler !== 'function' ?
          handler.handleEvent.apply( handler, argv ) :
          handler.apply( this, argv )
      }

      for( i = 0; i < len; i++ ) {
        Emitter.nextTick(
          fire.bind( this, listeners[i], argv )
        )
      }

      return true

    },

    /**
     * Execute each of the listeners in order
     * with the supplied arguments *synchronously*
     * @param  {String}  type
     * @return {Boolean}
     */
    emitSync: function( type ) {

      var emitter = this
      var listeners = this._events[ type ]

      if( type === 'error' && !listeners ) {
        if( !this._events.error ) {
          throw !( arguments[1] instanceof Error ) ?
            new Error( 'Unhandled "error" event.' ) :
            arguments[1]
        }
      } else if( !listeners ) {
        return false
      }

      var argv = [].slice.call( arguments, 1 )
      var handler, i, len = listeners.length

      for( i = 0; i < len; i++ ) {
        handler = listeners[i]
        typeof handler !== 'function'
          ? handler.handleEvent.apply( handler, argv )
          : handler.apply( this, argv )
      }

      return true

    },

    /**
     * Returns an array of listeners
     * for the specified event
     * @param  {String} type
     * @return {Array}
     */
    listeners: function( type ) {
      return this._events[ type ] ?
        this._events[ type ].slice() : []
    },

    /**
     * Sets the number of listeners that can
     * be added before a potential memory leak
     * warning is issued. Set to zero to disable.
     * @param {Number}   value
     * @return {Emitter}
     */
    setMaxListeners: function( value ) {

      if( typeof value !== 'number' )
        throw new TypeError( 'Value must be a number.' )

      this._maxListeners = value

      return this

    },

    /**
     * Remove a listener for the specified event
     * @param  {String}   type
     * @param  {Function} handler
     * @return {Emitter}
     */
    removeListener: function( type, handler ) {

      var handlers = this._events[ type ]
      var position = handlers.indexOf( handler )

      if( handlers && ~position ) {
        if( handlers.length === 1 ) {
          this._events[ type ] = undefined
          delete this._events[ type ]
        } else {
          handlers.splice( position, 1 )
        }
      }

      return this

    },

    /**
     * Removes all listeners,
     * or those of the specified event
     * @param  {String}  type
     * @return {Emitter}
     */
    removeAllListeners: function( type ) {

      if( arguments.length === 0 ) {
        for( type in this._events ) {
          this.removeAllListeners( type )
        }
      } else {
        this._events[ type ] = undefined
        delete this._events[ type ]
      }

      return this

    }

  }

})()
