// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: texthandling.proto

package kr.wise.advisor.prepare.textcluster.grpc;

/**
 * Protobuf type {@code kr.wise.advisor.prepare.textcluster.txtMchRequest}
 */
public  final class txtMchRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kr.wise.advisor.prepare.textcluster.txtMchRequest)
    txtMchRequestOrBuilder {
  // Use txtMchRequest.newBuilder() to construct.
  private txtMchRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private txtMchRequest() {
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private txtMchRequest(
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
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder subBuilder = null;
            if (sourceTb_ != null) {
              subBuilder = sourceTb_.toBuilder();
            }
            sourceTb_ = input.readMessage(kr.wise.advisor.prepare.textcluster.grpc.InfoDb.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(sourceTb_);
              sourceTb_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder subBuilder = null;
            if (targetTb_ != null) {
              subBuilder = targetTb_.toBuilder();
            }
            targetTb_ = input.readMessage(kr.wise.advisor.prepare.textcluster.grpc.InfoDb.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(targetTb_);
              targetTb_ = subBuilder.buildPartial();
            }

            break;
          }
          case 26: {
            kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder subBuilder = null;
            if (tgtDbms_ != null) {
              subBuilder = tgtDbms_.toBuilder();
            }
            tgtDbms_ = input.readMessage(kr.wise.advisor.prepare.summary.grpc.TargetDbms.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(tgtDbms_);
              tgtDbms_ = subBuilder.buildPartial();
            }

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
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_txtMchRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_txtMchRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.class, kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.Builder.class);
  }

  public static final int SOURCETB_FIELD_NUMBER = 1;
  private kr.wise.advisor.prepare.textcluster.grpc.InfoDb sourceTb_;
  /**
   * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
   */
  public boolean hasSourceTb() {
    return sourceTb_ != null;
  }
  /**
   * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
   */
  public kr.wise.advisor.prepare.textcluster.grpc.InfoDb getSourceTb() {
    return sourceTb_ == null ? kr.wise.advisor.prepare.textcluster.grpc.InfoDb.getDefaultInstance() : sourceTb_;
  }
  /**
   * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
   */
  public kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder getSourceTbOrBuilder() {
    return getSourceTb();
  }

  public static final int TARGETTB_FIELD_NUMBER = 2;
  private kr.wise.advisor.prepare.textcluster.grpc.InfoDb targetTb_;
  /**
   * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
   */
  public boolean hasTargetTb() {
    return targetTb_ != null;
  }
  /**
   * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
   */
  public kr.wise.advisor.prepare.textcluster.grpc.InfoDb getTargetTb() {
    return targetTb_ == null ? kr.wise.advisor.prepare.textcluster.grpc.InfoDb.getDefaultInstance() : targetTb_;
  }
  /**
   * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
   */
  public kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder getTargetTbOrBuilder() {
    return getTargetTb();
  }

  public static final int TGTDBMS_FIELD_NUMBER = 3;
  private kr.wise.advisor.prepare.summary.grpc.TargetDbms tgtDbms_;
  /**
   * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
   */
  public boolean hasTgtDbms() {
    return tgtDbms_ != null;
  }
  /**
   * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
   */
  public kr.wise.advisor.prepare.summary.grpc.TargetDbms getTgtDbms() {
    return tgtDbms_ == null ? kr.wise.advisor.prepare.summary.grpc.TargetDbms.getDefaultInstance() : tgtDbms_;
  }
  /**
   * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
   */
  public kr.wise.advisor.prepare.summary.grpc.TargetDbmsOrBuilder getTgtDbmsOrBuilder() {
    return getTgtDbms();
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
    if (sourceTb_ != null) {
      output.writeMessage(1, getSourceTb());
    }
    if (targetTb_ != null) {
      output.writeMessage(2, getTargetTb());
    }
    if (tgtDbms_ != null) {
      output.writeMessage(3, getTgtDbms());
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (sourceTb_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getSourceTb());
    }
    if (targetTb_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getTargetTb());
    }
    if (tgtDbms_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getTgtDbms());
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
    if (!(obj instanceof kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest)) {
      return super.equals(obj);
    }
    kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest other = (kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest) obj;

    boolean result = true;
    result = result && (hasSourceTb() == other.hasSourceTb());
    if (hasSourceTb()) {
      result = result && getSourceTb()
          .equals(other.getSourceTb());
    }
    result = result && (hasTargetTb() == other.hasTargetTb());
    if (hasTargetTb()) {
      result = result && getTargetTb()
          .equals(other.getTargetTb());
    }
    result = result && (hasTgtDbms() == other.hasTgtDbms());
    if (hasTgtDbms()) {
      result = result && getTgtDbms()
          .equals(other.getTgtDbms());
    }
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasSourceTb()) {
      hash = (37 * hash) + SOURCETB_FIELD_NUMBER;
      hash = (53 * hash) + getSourceTb().hashCode();
    }
    if (hasTargetTb()) {
      hash = (37 * hash) + TARGETTB_FIELD_NUMBER;
      hash = (53 * hash) + getTargetTb().hashCode();
    }
    if (hasTgtDbms()) {
      hash = (37 * hash) + TGTDBMS_FIELD_NUMBER;
      hash = (53 * hash) + getTgtDbms().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parseFrom(
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
  public static Builder newBuilder(kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest prototype) {
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
   * Protobuf type {@code kr.wise.advisor.prepare.textcluster.txtMchRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kr.wise.advisor.prepare.textcluster.txtMchRequest)
      kr.wise.advisor.prepare.textcluster.grpc.txtMchRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_txtMchRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_txtMchRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.class, kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.Builder.class);
    }

    // Construct using kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.newBuilder()
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
      if (sourceTbBuilder_ == null) {
        sourceTb_ = null;
      } else {
        sourceTb_ = null;
        sourceTbBuilder_ = null;
      }
      if (targetTbBuilder_ == null) {
        targetTb_ = null;
      } else {
        targetTb_ = null;
        targetTbBuilder_ = null;
      }
      if (tgtDbmsBuilder_ == null) {
        tgtDbms_ = null;
      } else {
        tgtDbms_ = null;
        tgtDbmsBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.internal_static_kr_wise_advisor_prepare_textcluster_txtMchRequest_descriptor;
    }

    public kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest getDefaultInstanceForType() {
      return kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.getDefaultInstance();
    }

    public kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest build() {
      kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest buildPartial() {
      kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest result = new kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest(this);
      if (sourceTbBuilder_ == null) {
        result.sourceTb_ = sourceTb_;
      } else {
        result.sourceTb_ = sourceTbBuilder_.build();
      }
      if (targetTbBuilder_ == null) {
        result.targetTb_ = targetTb_;
      } else {
        result.targetTb_ = targetTbBuilder_.build();
      }
      if (tgtDbmsBuilder_ == null) {
        result.tgtDbms_ = tgtDbms_;
      } else {
        result.tgtDbms_ = tgtDbmsBuilder_.build();
      }
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
      if (other instanceof kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest) {
        return mergeFrom((kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest other) {
      if (other == kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.getDefaultInstance()) return this;
      if (other.hasSourceTb()) {
        mergeSourceTb(other.getSourceTb());
      }
      if (other.hasTargetTb()) {
        mergeTargetTb(other.getTargetTb());
      }
      if (other.hasTgtDbms()) {
        mergeTgtDbms(other.getTgtDbms());
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
      kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private kr.wise.advisor.prepare.textcluster.grpc.InfoDb sourceTb_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        kr.wise.advisor.prepare.textcluster.grpc.InfoDb, kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder, kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder> sourceTbBuilder_;
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public boolean hasSourceTb() {
      return sourceTbBuilder_ != null || sourceTb_ != null;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public kr.wise.advisor.prepare.textcluster.grpc.InfoDb getSourceTb() {
      if (sourceTbBuilder_ == null) {
        return sourceTb_ == null ? kr.wise.advisor.prepare.textcluster.grpc.InfoDb.getDefaultInstance() : sourceTb_;
      } else {
        return sourceTbBuilder_.getMessage();
      }
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public Builder setSourceTb(kr.wise.advisor.prepare.textcluster.grpc.InfoDb value) {
      if (sourceTbBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        sourceTb_ = value;
        onChanged();
      } else {
        sourceTbBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public Builder setSourceTb(
        kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder builderForValue) {
      if (sourceTbBuilder_ == null) {
        sourceTb_ = builderForValue.build();
        onChanged();
      } else {
        sourceTbBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public Builder mergeSourceTb(kr.wise.advisor.prepare.textcluster.grpc.InfoDb value) {
      if (sourceTbBuilder_ == null) {
        if (sourceTb_ != null) {
          sourceTb_ =
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb.newBuilder(sourceTb_).mergeFrom(value).buildPartial();
        } else {
          sourceTb_ = value;
        }
        onChanged();
      } else {
        sourceTbBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public Builder clearSourceTb() {
      if (sourceTbBuilder_ == null) {
        sourceTb_ = null;
        onChanged();
      } else {
        sourceTb_ = null;
        sourceTbBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder getSourceTbBuilder() {
      
      onChanged();
      return getSourceTbFieldBuilder().getBuilder();
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    public kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder getSourceTbOrBuilder() {
      if (sourceTbBuilder_ != null) {
        return sourceTbBuilder_.getMessageOrBuilder();
      } else {
        return sourceTb_ == null ?
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb.getDefaultInstance() : sourceTb_;
      }
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb sourceTb = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        kr.wise.advisor.prepare.textcluster.grpc.InfoDb, kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder, kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder> 
        getSourceTbFieldBuilder() {
      if (sourceTbBuilder_ == null) {
        sourceTbBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb, kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder, kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder>(
                getSourceTb(),
                getParentForChildren(),
                isClean());
        sourceTb_ = null;
      }
      return sourceTbBuilder_;
    }

    private kr.wise.advisor.prepare.textcluster.grpc.InfoDb targetTb_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        kr.wise.advisor.prepare.textcluster.grpc.InfoDb, kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder, kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder> targetTbBuilder_;
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public boolean hasTargetTb() {
      return targetTbBuilder_ != null || targetTb_ != null;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public kr.wise.advisor.prepare.textcluster.grpc.InfoDb getTargetTb() {
      if (targetTbBuilder_ == null) {
        return targetTb_ == null ? kr.wise.advisor.prepare.textcluster.grpc.InfoDb.getDefaultInstance() : targetTb_;
      } else {
        return targetTbBuilder_.getMessage();
      }
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public Builder setTargetTb(kr.wise.advisor.prepare.textcluster.grpc.InfoDb value) {
      if (targetTbBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        targetTb_ = value;
        onChanged();
      } else {
        targetTbBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public Builder setTargetTb(
        kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder builderForValue) {
      if (targetTbBuilder_ == null) {
        targetTb_ = builderForValue.build();
        onChanged();
      } else {
        targetTbBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public Builder mergeTargetTb(kr.wise.advisor.prepare.textcluster.grpc.InfoDb value) {
      if (targetTbBuilder_ == null) {
        if (targetTb_ != null) {
          targetTb_ =
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb.newBuilder(targetTb_).mergeFrom(value).buildPartial();
        } else {
          targetTb_ = value;
        }
        onChanged();
      } else {
        targetTbBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public Builder clearTargetTb() {
      if (targetTbBuilder_ == null) {
        targetTb_ = null;
        onChanged();
      } else {
        targetTb_ = null;
        targetTbBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder getTargetTbBuilder() {
      
      onChanged();
      return getTargetTbFieldBuilder().getBuilder();
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    public kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder getTargetTbOrBuilder() {
      if (targetTbBuilder_ != null) {
        return targetTbBuilder_.getMessageOrBuilder();
      } else {
        return targetTb_ == null ?
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb.getDefaultInstance() : targetTb_;
      }
    }
    /**
     * <code>.kr.wise.advisor.prepare.textcluster.InfoDb targetTb = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        kr.wise.advisor.prepare.textcluster.grpc.InfoDb, kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder, kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder> 
        getTargetTbFieldBuilder() {
      if (targetTbBuilder_ == null) {
        targetTbBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            kr.wise.advisor.prepare.textcluster.grpc.InfoDb, kr.wise.advisor.prepare.textcluster.grpc.InfoDb.Builder, kr.wise.advisor.prepare.textcluster.grpc.InfoDbOrBuilder>(
                getTargetTb(),
                getParentForChildren(),
                isClean());
        targetTb_ = null;
      }
      return targetTbBuilder_;
    }

    private kr.wise.advisor.prepare.summary.grpc.TargetDbms tgtDbms_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        kr.wise.advisor.prepare.summary.grpc.TargetDbms, kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder, kr.wise.advisor.prepare.summary.grpc.TargetDbmsOrBuilder> tgtDbmsBuilder_;
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public boolean hasTgtDbms() {
      return tgtDbmsBuilder_ != null || tgtDbms_ != null;
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public kr.wise.advisor.prepare.summary.grpc.TargetDbms getTgtDbms() {
      if (tgtDbmsBuilder_ == null) {
        return tgtDbms_ == null ? kr.wise.advisor.prepare.summary.grpc.TargetDbms.getDefaultInstance() : tgtDbms_;
      } else {
        return tgtDbmsBuilder_.getMessage();
      }
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public Builder setTgtDbms(kr.wise.advisor.prepare.summary.grpc.TargetDbms value) {
      if (tgtDbmsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        tgtDbms_ = value;
        onChanged();
      } else {
        tgtDbmsBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public Builder setTgtDbms(
        kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder builderForValue) {
      if (tgtDbmsBuilder_ == null) {
        tgtDbms_ = builderForValue.build();
        onChanged();
      } else {
        tgtDbmsBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public Builder mergeTgtDbms(kr.wise.advisor.prepare.summary.grpc.TargetDbms value) {
      if (tgtDbmsBuilder_ == null) {
        if (tgtDbms_ != null) {
          tgtDbms_ =
            kr.wise.advisor.prepare.summary.grpc.TargetDbms.newBuilder(tgtDbms_).mergeFrom(value).buildPartial();
        } else {
          tgtDbms_ = value;
        }
        onChanged();
      } else {
        tgtDbmsBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public Builder clearTgtDbms() {
      if (tgtDbmsBuilder_ == null) {
        tgtDbms_ = null;
        onChanged();
      } else {
        tgtDbms_ = null;
        tgtDbmsBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder getTgtDbmsBuilder() {
      
      onChanged();
      return getTgtDbmsFieldBuilder().getBuilder();
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    public kr.wise.advisor.prepare.summary.grpc.TargetDbmsOrBuilder getTgtDbmsOrBuilder() {
      if (tgtDbmsBuilder_ != null) {
        return tgtDbmsBuilder_.getMessageOrBuilder();
      } else {
        return tgtDbms_ == null ?
            kr.wise.advisor.prepare.summary.grpc.TargetDbms.getDefaultInstance() : tgtDbms_;
      }
    }
    /**
     * <code>.kr.wise.advisor.prepare.summary.TargetDbms tgtDbms = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        kr.wise.advisor.prepare.summary.grpc.TargetDbms, kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder, kr.wise.advisor.prepare.summary.grpc.TargetDbmsOrBuilder> 
        getTgtDbmsFieldBuilder() {
      if (tgtDbmsBuilder_ == null) {
        tgtDbmsBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            kr.wise.advisor.prepare.summary.grpc.TargetDbms, kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder, kr.wise.advisor.prepare.summary.grpc.TargetDbmsOrBuilder>(
                getTgtDbms(),
                getParentForChildren(),
                isClean());
        tgtDbms_ = null;
      }
      return tgtDbmsBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:kr.wise.advisor.prepare.textcluster.txtMchRequest)
  }

  // @@protoc_insertion_point(class_scope:kr.wise.advisor.prepare.textcluster.txtMchRequest)
  private static final kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest();
  }

  public static kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<txtMchRequest>
      PARSER = new com.google.protobuf.AbstractParser<txtMchRequest>() {
    public txtMchRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new txtMchRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<txtMchRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<txtMchRequest> getParserForType() {
    return PARSER;
  }

  public kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

