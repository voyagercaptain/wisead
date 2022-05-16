// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: texthandling.proto

package kr.wise.advisor.prepare.textcluster.grpc;

/**
 * Protobuf type {@code kr.wise.advisor.prepare.textcluster.TxtClstr}
 */
public  final class TxtClstr extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kr.wise.advisor.prepare.textcluster.TxtClstr)
    TxtClstrOrBuilder {
  // Use TxtClstr.newBuilder() to construct.
  private TxtClstr(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TxtClstr() {
    recommand_ = "";
    result_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private TxtClstr(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            recommand_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              result_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000002;
            }
            result_.add(s);
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        result_ = result_.getUnmodifiableView();
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_TxtClstr_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_TxtClstr_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.class, kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.Builder.class);
  }

  private int bitField0_;
  public static final int RECOMMAND_FIELD_NUMBER = 1;
  private volatile java.lang.Object recommand_;
  /**
   * <code>string recommand = 1;</code>
   */
  public java.lang.String getRecommand() {
    java.lang.Object ref = recommand_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      recommand_ = s;
      return s;
    }
  }
  /**
   * <code>string recommand = 1;</code>
   */
  public com.google.protobuf.ByteString
      getRecommandBytes() {
    java.lang.Object ref = recommand_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      recommand_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RESULT_FIELD_NUMBER = 2;
  private com.google.protobuf.LazyStringList result_;
  /**
   * <code>repeated string result = 2;</code>
   */
  public com.google.protobuf.ProtocolStringList
      getResultList() {
    return result_;
  }
  /**
   * <code>repeated string result = 2;</code>
   */
  public int getResultCount() {
    return result_.size();
  }
  /**
   * <code>repeated string result = 2;</code>
   */
  public java.lang.String getResult(int index) {
    return result_.get(index);
  }
  /**
   * <code>repeated string result = 2;</code>
   */
  public com.google.protobuf.ByteString
      getResultBytes(int index) {
    return result_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getRecommandBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, recommand_);
    }
    for (int i = 0; i < result_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, result_.getRaw(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getRecommandBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, recommand_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < result_.size(); i++) {
        dataSize += computeStringSizeNoTag(result_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getResultList().size();
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof kr.wise.advisor.prepare.textcluster.grpc.TxtClstr)) {
      return super.equals(obj);
    }
    kr.wise.advisor.prepare.textcluster.grpc.TxtClstr other = (kr.wise.advisor.prepare.textcluster.grpc.TxtClstr) obj;

    boolean result = true;
    result = result && getRecommand()
        .equals(other.getRecommand());
    result = result && getResultList()
        .equals(other.getResultList());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + RECOMMAND_FIELD_NUMBER;
    hash = (53 * hash) + getRecommand().hashCode();
    if (getResultCount() > 0) {
      hash = (37 * hash) + RESULT_FIELD_NUMBER;
      hash = (53 * hash) + getResultList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(kr.wise.advisor.prepare.textcluster.grpc.TxtClstr prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code kr.wise.advisor.prepare.textcluster.TxtClstr}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kr.wise.advisor.prepare.textcluster.TxtClstr)
      kr.wise.advisor.prepare.textcluster.grpc.TxtClstrOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_TxtClstr_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_TxtClstr_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.class, kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.Builder.class);
    }

    // Construct using kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      recommand_ = "";

      result_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_TxtClstr_descriptor;
    }

    public kr.wise.advisor.prepare.textcluster.grpc.TxtClstr getDefaultInstanceForType() {
      return kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.getDefaultInstance();
    }

    public kr.wise.advisor.prepare.textcluster.grpc.TxtClstr build() {
      kr.wise.advisor.prepare.textcluster.grpc.TxtClstr result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public kr.wise.advisor.prepare.textcluster.grpc.TxtClstr buildPartial() {
      kr.wise.advisor.prepare.textcluster.grpc.TxtClstr result = new kr.wise.advisor.prepare.textcluster.grpc.TxtClstr(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.recommand_ = recommand_;
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        result_ = result_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000002);
      }
      result.result_ = result_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof kr.wise.advisor.prepare.textcluster.grpc.TxtClstr) {
        return mergeFrom((kr.wise.advisor.prepare.textcluster.grpc.TxtClstr)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(kr.wise.advisor.prepare.textcluster.grpc.TxtClstr other) {
      if (other == kr.wise.advisor.prepare.textcluster.grpc.TxtClstr.getDefaultInstance()) return this;
      if (!other.getRecommand().isEmpty()) {
        recommand_ = other.recommand_;
        onChanged();
      }
      if (!other.result_.isEmpty()) {
        if (result_.isEmpty()) {
          result_ = other.result_;
          bitField0_ = (bitField0_ & ~0x00000002);
        } else {
          ensureResultIsMutable();
          result_.addAll(other.result_);
        }
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      kr.wise.advisor.prepare.textcluster.grpc.TxtClstr parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (kr.wise.advisor.prepare.textcluster.grpc.TxtClstr) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object recommand_ = "";
    /**
     * <code>string recommand = 1;</code>
     */
    public java.lang.String getRecommand() {
      java.lang.Object ref = recommand_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        recommand_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string recommand = 1;</code>
     */
    public com.google.protobuf.ByteString
        getRecommandBytes() {
      java.lang.Object ref = recommand_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        recommand_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string recommand = 1;</code>
     */
    public Builder setRecommand(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      recommand_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string recommand = 1;</code>
     */
    public Builder clearRecommand() {
      
      recommand_ = getDefaultInstance().getRecommand();
      onChanged();
      return this;
    }
    /**
     * <code>string recommand = 1;</code>
     */
    public Builder setRecommandBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      recommand_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList result_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureResultIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        result_ = new com.google.protobuf.LazyStringArrayList(result_);
        bitField0_ |= 0x00000002;
       }
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getResultList() {
      return result_.getUnmodifiableView();
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public int getResultCount() {
      return result_.size();
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public java.lang.String getResult(int index) {
      return result_.get(index);
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public com.google.protobuf.ByteString
        getResultBytes(int index) {
      return result_.getByteString(index);
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public Builder setResult(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureResultIsMutable();
      result_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public Builder addResult(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureResultIsMutable();
      result_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public Builder addAllResult(
        java.lang.Iterable<java.lang.String> values) {
      ensureResultIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, result_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public Builder clearResult() {
      result_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string result = 2;</code>
     */
    public Builder addResultBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureResultIsMutable();
      result_.add(value);
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:kr.wise.advisor.prepare.textcluster.TxtClstr)
  }

  // @@protoc_insertion_point(class_scope:kr.wise.advisor.prepare.textcluster.TxtClstr)
  private static final kr.wise.advisor.prepare.textcluster.grpc.TxtClstr DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new kr.wise.advisor.prepare.textcluster.grpc.TxtClstr();
  }

  public static kr.wise.advisor.prepare.textcluster.grpc.TxtClstr getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TxtClstr>
      PARSER = new com.google.protobuf.AbstractParser<TxtClstr>() {
    public TxtClstr parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new TxtClstr(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<TxtClstr> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TxtClstr> getParserForType() {
    return PARSER;
  }

  public kr.wise.advisor.prepare.textcluster.grpc.TxtClstr getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

