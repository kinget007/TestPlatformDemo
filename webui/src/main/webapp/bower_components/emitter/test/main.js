
var assert = require( 'assert' )
var Emitter = require( '../' )

describe( 'Emitter', function() {

  describe( '#emit( \'error\' )', function() {

    it( 'should throw on uncaught error events', function() {
      var emitter = new Emitter()
      assert.throws( function() {
        emitter.emit( 'error' )
      })
    })

    it( 'should throw passed errors', function() {
      var emitter = new Emitter()
      var error = new Error( 'Test' )
      try {
        emitter.emit( 'error', error )
      } catch( e ) {
        assert.deepEqual( e, error )
      }
    })

    it( 'should throw any instance of a passed error', function() {

      var emitter = new Emitter()

      var typeError = new TypeError( 'Test' )
      try { emitter.emit( 'error', typeError ) }
      catch( e ) { assert.deepEqual( e, typeError ) }

      function CustomError() { Error.call( this ) }
      CustomError.prototype.__proto__ = Error.prototype

      var customError = new CustomError( 'Test' )
      try { emitter.emit( 'error', customError ) }
      catch( e ) { assert.deepEqual( e, customError ) }

    })

    it( 'should support the EventListener interface', function() {

      var emitter = new Emitter()
      var handled = false
      var object = {
        handleEvent: function() {
          handled = true
        }
      }

      emitter.on( 'interface', object )
      emitter.emitSync( 'interface' )

      assert.ok( handled )

      emitter.removeListener( 'interface', object )

      assert.ok( !emitter._events[ 'interface' ] )

    })

  })

})
