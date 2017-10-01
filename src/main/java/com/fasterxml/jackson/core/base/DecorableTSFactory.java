package com.fasterxml.jackson.core.base;

import java.io.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.*;

/**
 * Intermediate base {@link TokenStreamFactory} implementation that offers support for
 * streams that allow decoration of low-level input sources and output targets.
 *
 * @since 3.0
 */
public abstract class DecorableTSFactory
    extends TokenStreamFactory
{
    /*
    /**********************************************************
    /* Configuration
    /**********************************************************
     */

    /**
     * Object that implements conversion functionality between
     * Java objects and JSON content. For base JsonFactory implementation
     * usually not set by default, but can be explicitly set.
     * Sub-classes (like @link org.codehaus.jackson.map.MappingJsonFactory}
     * usually provide an implementation.
     */
    protected ObjectCodec _objectCodec;

    /**
     * Optional helper object that may decorate input sources, to do
     * additional processing on input during parsing.
     */
    protected InputDecorator _inputDecorator;

    /**
     * Optional helper object that may decorate output object, to do
     * additional processing on output during content generation.
     */
    protected OutputDecorator _outputDecorator;

    /*
    /**********************************************************
    /* Construction
    /**********************************************************
     */
    
    protected DecorableTSFactory() { this(null); }

    protected DecorableTSFactory(ObjectCodec oc) {
        super();
        _objectCodec = oc;
    }

    /**
     * Constructor used when copy()ing a factory instance.
     */
    protected DecorableTSFactory(DecorableTSFactory src, ObjectCodec codec) {
        super(src);
        _objectCodec = codec;
    }

    /*
    /**********************************************************
    /* Configuration
    /**********************************************************
     */

    @Override
    public DecorableTSFactory setCodec(ObjectCodec oc) {
        _objectCodec = oc;
        return this;
    }

    @Override
    public ObjectCodec getCodec() { return _objectCodec; }

    @Override
    public OutputDecorator getOutputDecorator() {
        return _outputDecorator;
    }

    @Override
    public DecorableTSFactory setOutputDecorator(OutputDecorator d) {
        _outputDecorator = d;
        return this;
    }

    @Override
    public InputDecorator getInputDecorator() {
        return _inputDecorator;
    }

    @Override
    public DecorableTSFactory setInputDecorator(InputDecorator d) {
        _inputDecorator = d;
        return this;
    }

    /*
    /**********************************************************
    /* Decorators, input
    /**********************************************************
     */

    protected InputStream _decorate(InputStream in, IOContext ctxt) throws IOException
    {
        if (_inputDecorator != null) {
            InputStream in2 = _inputDecorator.decorate(ctxt, in);
            if (in2 != null) {
                return in2;
            }
        }
        return in;
    }

    protected Reader _decorate(Reader in, IOContext ctxt) throws IOException
    {
        if (_inputDecorator != null) {
            Reader in2 = _inputDecorator.decorate(ctxt, in);
            if (in2 != null) {
                return in2;
            }
        }
        return in;
    }

    protected DataInput _decorate(DataInput in, IOContext ctxt) throws IOException
    {
        if (_inputDecorator != null) {
            DataInput in2 = _inputDecorator.decorate(ctxt, in);
            if (in2 != null) {
                return in2;
            }
        }
        return in;
    }

    /*
    /**********************************************************
    /* Decorators, output
    /**********************************************************
     */

    protected OutputStream _decorate(OutputStream out, IOContext ctxt) throws IOException
    {
        if (_outputDecorator != null) {
            OutputStream out2 = _outputDecorator.decorate(ctxt, out);
            if (out2 != null) {
                return out2;
            }
        }
        return out;
    }

    protected Writer _decorate(Writer out, IOContext ctxt) throws IOException
    {
        if (_outputDecorator != null) {
            Writer out2 = _outputDecorator.decorate(ctxt, out);
            if (out2 != null) {
                return out2;
            }
        }
        return out;
    }
}
