/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.mercury.serialization.avro.structure;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class AvroBinaryMessage extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5916830266823486498L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvroBinaryMessage\",\"namespace\":\"io.mercury.serialization.avro.structure\",\"fields\":[{\"name\":\"sequence\",\"type\":\"long\"},{\"name\":\"envelope\",\"type\":{\"type\":\"record\",\"name\":\"Envelope\",\"fields\":[{\"name\":\"version\",\"type\":\"int\"},{\"name\":\"type\",\"type\":\"int\"},{\"name\":\"contentType\",\"type\":{\"type\":\"enum\",\"name\":\"ContentType\",\"symbols\":[\"OBJECT\",\"LIST\",\"STRING\"]}}]}},{\"name\":\"content\",\"type\":\"bytes\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<AvroBinaryMessage> ENCODER =
      new BinaryMessageEncoder<AvroBinaryMessage>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<AvroBinaryMessage> DECODER =
      new BinaryMessageDecoder<AvroBinaryMessage>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<AvroBinaryMessage> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<AvroBinaryMessage> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<AvroBinaryMessage> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<AvroBinaryMessage>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this AvroBinaryMessage to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a AvroBinaryMessage from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a AvroBinaryMessage instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static AvroBinaryMessage fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private long sequence;
   private io.mercury.serialization.avro.structure.Envelope envelope;
   private java.nio.ByteBuffer content;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AvroBinaryMessage() {}

  /**
   * All-args constructor.
   * @param sequence The new value for sequence
   * @param envelope The new value for envelope
   * @param content The new value for content
   */
  public AvroBinaryMessage(java.lang.Long sequence, io.mercury.serialization.avro.structure.Envelope envelope, java.nio.ByteBuffer content) {
    this.sequence = sequence;
    this.envelope = envelope;
    this.content = content;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return sequence;
    case 1: return envelope;
    case 2: return content;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: sequence = (java.lang.Long)value$; break;
    case 1: envelope = (io.mercury.serialization.avro.structure.Envelope)value$; break;
    case 2: content = (java.nio.ByteBuffer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'sequence' field.
   * @return The value of the 'sequence' field.
   */
  public long getSequence() {
    return sequence;
  }


  /**
   * Sets the value of the 'sequence' field.
   * @param value the value to set.
   */
  public void setSequence(long value) {
    this.sequence = value;
  }

  /**
   * Gets the value of the 'envelope' field.
   * @return The value of the 'envelope' field.
   */
  public io.mercury.serialization.avro.structure.Envelope getEnvelope() {
    return envelope;
  }


  /**
   * Sets the value of the 'envelope' field.
   * @param value the value to set.
   */
  public void setEnvelope(io.mercury.serialization.avro.structure.Envelope value) {
    this.envelope = value;
  }

  /**
   * Gets the value of the 'content' field.
   * @return The value of the 'content' field.
   */
  public java.nio.ByteBuffer getContent() {
    return content;
  }


  /**
   * Sets the value of the 'content' field.
   * @param value the value to set.
   */
  public void setContent(java.nio.ByteBuffer value) {
    this.content = value;
  }

  /**
   * Creates a new AvroBinaryMessage RecordBuilder.
   * @return A new AvroBinaryMessage RecordBuilder
   */
  public static io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder newBuilder() {
    return new io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder();
  }

  /**
   * Creates a new AvroBinaryMessage RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AvroBinaryMessage RecordBuilder
   */
  public static io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder newBuilder(io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder other) {
    if (other == null) {
      return new io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder();
    } else {
      return new io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder(other);
    }
  }

  /**
   * Creates a new AvroBinaryMessage RecordBuilder by copying an existing AvroBinaryMessage instance.
   * @param other The existing instance to copy.
   * @return A new AvroBinaryMessage RecordBuilder
   */
  public static io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder newBuilder(io.mercury.serialization.avro.structure.AvroBinaryMessage other) {
    if (other == null) {
      return new io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder();
    } else {
      return new io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder(other);
    }
  }

  /**
   * RecordBuilder for AvroBinaryMessage instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvroBinaryMessage>
    implements org.apache.avro.data.RecordBuilder<AvroBinaryMessage> {

    private long sequence;
    private io.mercury.serialization.avro.structure.Envelope envelope;
    private io.mercury.serialization.avro.structure.Envelope.Builder envelopeBuilder;
    private java.nio.ByteBuffer content;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.sequence)) {
        this.sequence = data().deepCopy(fields()[0].schema(), other.sequence);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.envelope)) {
        this.envelope = data().deepCopy(fields()[1].schema(), other.envelope);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasEnvelopeBuilder()) {
        this.envelopeBuilder = io.mercury.serialization.avro.structure.Envelope.newBuilder(other.getEnvelopeBuilder());
      }
      if (isValidValue(fields()[2], other.content)) {
        this.content = data().deepCopy(fields()[2].schema(), other.content);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing AvroBinaryMessage instance
     * @param other The existing instance to copy.
     */
    private Builder(io.mercury.serialization.avro.structure.AvroBinaryMessage other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.sequence)) {
        this.sequence = data().deepCopy(fields()[0].schema(), other.sequence);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.envelope)) {
        this.envelope = data().deepCopy(fields()[1].schema(), other.envelope);
        fieldSetFlags()[1] = true;
      }
      this.envelopeBuilder = null;
      if (isValidValue(fields()[2], other.content)) {
        this.content = data().deepCopy(fields()[2].schema(), other.content);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'sequence' field.
      * @return The value.
      */
    public long getSequence() {
      return sequence;
    }


    /**
      * Sets the value of the 'sequence' field.
      * @param value The value of 'sequence'.
      * @return This builder.
      */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder setSequence(long value) {
      validate(fields()[0], value);
      this.sequence = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'sequence' field has been set.
      * @return True if the 'sequence' field has been set, false otherwise.
      */
    public boolean hasSequence() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'sequence' field.
      * @return This builder.
      */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder clearSequence() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'envelope' field.
      * @return The value.
      */
    public io.mercury.serialization.avro.structure.Envelope getEnvelope() {
      return envelope;
    }


    /**
      * Sets the value of the 'envelope' field.
      * @param value The value of 'envelope'.
      * @return This builder.
      */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder setEnvelope(io.mercury.serialization.avro.structure.Envelope value) {
      validate(fields()[1], value);
      this.envelopeBuilder = null;
      this.envelope = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'envelope' field has been set.
      * @return True if the 'envelope' field has been set, false otherwise.
      */
    public boolean hasEnvelope() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'envelope' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public io.mercury.serialization.avro.structure.Envelope.Builder getEnvelopeBuilder() {
      if (envelopeBuilder == null) {
        if (hasEnvelope()) {
          setEnvelopeBuilder(io.mercury.serialization.avro.structure.Envelope.newBuilder(envelope));
        } else {
          setEnvelopeBuilder(io.mercury.serialization.avro.structure.Envelope.newBuilder());
        }
      }
      return envelopeBuilder;
    }

    /**
     * Sets the Builder instance for the 'envelope' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder setEnvelopeBuilder(io.mercury.serialization.avro.structure.Envelope.Builder value) {
      clearEnvelope();
      envelopeBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'envelope' field has an active Builder instance
     * @return True if the 'envelope' field has an active Builder instance
     */
    public boolean hasEnvelopeBuilder() {
      return envelopeBuilder != null;
    }

    /**
      * Clears the value of the 'envelope' field.
      * @return This builder.
      */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder clearEnvelope() {
      envelope = null;
      envelopeBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'content' field.
      * @return The value.
      */
    public java.nio.ByteBuffer getContent() {
      return content;
    }


    /**
      * Sets the value of the 'content' field.
      * @param value The value of 'content'.
      * @return This builder.
      */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder setContent(java.nio.ByteBuffer value) {
      validate(fields()[2], value);
      this.content = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'content' field has been set.
      * @return True if the 'content' field has been set, false otherwise.
      */
    public boolean hasContent() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'content' field.
      * @return This builder.
      */
    public io.mercury.serialization.avro.structure.AvroBinaryMessage.Builder clearContent() {
      content = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public AvroBinaryMessage build() {
      try {
        AvroBinaryMessage record = new AvroBinaryMessage();
        record.sequence = fieldSetFlags()[0] ? this.sequence : (java.lang.Long) defaultValue(fields()[0]);
        if (envelopeBuilder != null) {
          try {
            record.envelope = this.envelopeBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("envelope"));
            throw e;
          }
        } else {
          record.envelope = fieldSetFlags()[1] ? this.envelope : (io.mercury.serialization.avro.structure.Envelope) defaultValue(fields()[1]);
        }
        record.content = fieldSetFlags()[2] ? this.content : (java.nio.ByteBuffer) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<AvroBinaryMessage>
    WRITER$ = (org.apache.avro.io.DatumWriter<AvroBinaryMessage>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<AvroBinaryMessage>
    READER$ = (org.apache.avro.io.DatumReader<AvroBinaryMessage>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeLong(this.sequence);

    this.envelope.customEncode(out);

    out.writeBytes(this.content);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.sequence = in.readLong();

      if (this.envelope == null) {
        this.envelope = new io.mercury.serialization.avro.structure.Envelope();
      }
      this.envelope.customDecode(in);

      this.content = in.readBytes(this.content);

    } else {
      for (int i = 0; i < 3; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.sequence = in.readLong();
          break;

        case 1:
          if (this.envelope == null) {
            this.envelope = new io.mercury.serialization.avro.structure.Envelope();
          }
          this.envelope.customDecode(in);
          break;

        case 2:
          this.content = in.readBytes(this.content);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










