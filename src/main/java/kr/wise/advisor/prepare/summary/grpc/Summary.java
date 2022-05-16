// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: summary.proto

package kr.wise.advisor.prepare.summary.grpc;

/**
 * <pre>
 *summary 응답...
 * </pre>
 *
 * Protobuf type {@code kr.wise.advisor.prepare.summary.Summary}
 */
public  final class Summary extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kr.wise.advisor.prepare.summary.Summary)
    SummaryOrBuilder {
  // Use Summary.newBuilder() to construct.
  private Summary(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Summary() {
    name_ = "";
    count_ = 0;
    mean_ = 0F;
    min_ = 0F;
    std_ = 0F;
    q1_ = 0F;
    q2_ = 0F;
    q3_ = 0F;
    max_ = 0F;
    unique_ = 0;
    top_ = "";
    freq_ = 0;
    type_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Summary(
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
          case 8: {

            count_ = input.readUInt32();
            break;
          }
          case 21: {

            mean_ = input.readFloat();
            break;
          }
          case 29: {

            min_ = input.readFloat();
            break;
          }
          case 37: {

            std_ = input.readFloat();
            break;
          }
          case 45: {

            q1_ = input.readFloat();
            break;
          }
          case 53: {

            q2_ = input.readFloat();
            break;
          }
          case 61: {

            q3_ = input.readFloat();
            break;
          }
          case 69: {

            max_ = input.readFloat();
            break;
          }
          case 72: {

            unique_ = input.readUInt32();
            break;
          }
          case 82: {
            java.lang.String s = input.readStringRequireUtf8();

            top_ = s;
            break;
          }
          case 88: {

            freq_ = input.readUInt32();
            break;
          }
          case 98: {
            java.lang.String s = input.readStringRequireUtf8();

            type_ = s;
            break;
          }
          case 106: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
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
    return kr.wise.advisor.prepare.summary.grpc.SummaryProcess.internal_static_kr_wise_advisor_prepare_summary_Summary_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return kr.wise.advisor.prepare.summary.grpc.SummaryProcess.internal_static_kr_wise_advisor_prepare_summary_Summary_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            kr.wise.advisor.prepare.summary.grpc.Summary.class, kr.wise.advisor.prepare.summary.grpc.Summary.Builder.class);
  }

  public static final int NAME_FIELD_NUMBER = 13;
  private volatile java.lang.Object name_;
  /**
   * <pre>
   *컬럼명
   * </pre>
   *
   * <code>string name = 13;</code>
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *컬럼명
   * </pre>
   *
   * <code>string name = 13;</code>
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int COUNT_FIELD_NUMBER = 1;
  private int count_;
  /**
   * <pre>
   *전체수
   * </pre>
   *
   * <code>uint32 count = 1;</code>
   */
  public int getCount() {
    return count_;
  }

  public static final int MEAN_FIELD_NUMBER = 2;
  private float mean_;
  /**
   * <pre>
   *평균
   * </pre>
   *
   * <code>float mean = 2;</code>
   */
  public float getMean() {
    return mean_;
  }

  public static final int MIN_FIELD_NUMBER = 3;
  private float min_;
  /**
   * <pre>
   *최소값
   * </pre>
   *
   * <code>float min = 3;</code>
   */
  public float getMin() {
    return min_;
  }

  public static final int STD_FIELD_NUMBER = 4;
  private float std_;
  /**
   * <pre>
   *표준편차
   * </pre>
   *
   * <code>float std = 4;</code>
   */
  public float getStd() {
    return std_;
  }

  public static final int Q1_FIELD_NUMBER = 5;
  private float q1_;
  /**
   * <pre>
   *1분위수
   * </pre>
   *
   * <code>float q1 = 5;</code>
   */
  public float getQ1() {
    return q1_;
  }

  public static final int Q2_FIELD_NUMBER = 6;
  private float q2_;
  /**
   * <pre>
   *2분위수(중앙값)
   * </pre>
   *
   * <code>float q2 = 6;</code>
   */
  public float getQ2() {
    return q2_;
  }

  public static final int Q3_FIELD_NUMBER = 7;
  private float q3_;
  /**
   * <pre>
   *3분위수
   * </pre>
   *
   * <code>float q3 = 7;</code>
   */
  public float getQ3() {
    return q3_;
  }

  public static final int MAX_FIELD_NUMBER = 8;
  private float max_;
  /**
   * <pre>
   *최대값
   * </pre>
   *
   * <code>float max = 8;</code>
   */
  public float getMax() {
    return max_;
  }

  public static final int UNIQUE_FIELD_NUMBER = 9;
  private int unique_;
  /**
   * <pre>
   *유니크수
   * </pre>
   *
   * <code>uint32 unique = 9;</code>
   */
  public int getUnique() {
    return unique_;
  }

  public static final int TOP_FIELD_NUMBER = 10;
  private volatile java.lang.Object top_;
  /**
   * <pre>
   *최빈값
   * </pre>
   *
   * <code>string top = 10;</code>
   */
  public java.lang.String getTop() {
    java.lang.Object ref = top_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      top_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *최빈값
   * </pre>
   *
   * <code>string top = 10;</code>
   */
  public com.google.protobuf.ByteString
      getTopBytes() {
    java.lang.Object ref = top_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      top_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FREQ_FIELD_NUMBER = 11;
  private int freq_;
  /**
   * <pre>
   *최빈값 갯수
   * </pre>
   *
   * <code>uint32 freq = 11;</code>
   */
  public int getFreq() {
    return freq_;
  }

  public static final int TYPE_FIELD_NUMBER = 12;
  private volatile java.lang.Object type_;
  /**
   * <pre>
   *변수타입
   * </pre>
   *
   * <code>string type = 12;</code>
   */
  public java.lang.String getType() {
    java.lang.Object ref = type_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      type_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *변수타입
   * </pre>
   *
   * <code>string type = 12;</code>
   */
  public com.google.protobuf.ByteString
      getTypeBytes() {
    java.lang.Object ref = type_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      type_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (count_ != 0) {
      output.writeUInt32(1, count_);
    }
    if (mean_ != 0F) {
      output.writeFloat(2, mean_);
    }
    if (min_ != 0F) {
      output.writeFloat(3, min_);
    }
    if (std_ != 0F) {
      output.writeFloat(4, std_);
    }
    if (q1_ != 0F) {
      output.writeFloat(5, q1_);
    }
    if (q2_ != 0F) {
      output.writeFloat(6, q2_);
    }
    if (q3_ != 0F) {
      output.writeFloat(7, q3_);
    }
    if (max_ != 0F) {
      output.writeFloat(8, max_);
    }
    if (unique_ != 0) {
      output.writeUInt32(9, unique_);
    }
    if (!getTopBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 10, top_);
    }
    if (freq_ != 0) {
      output.writeUInt32(11, freq_);
    }
    if (!getTypeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 12, type_);
    }
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 13, name_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (count_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, count_);
    }
    if (mean_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(2, mean_);
    }
    if (min_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(3, min_);
    }
    if (std_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(4, std_);
    }
    if (q1_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(5, q1_);
    }
    if (q2_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(6, q2_);
    }
    if (q3_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(7, q3_);
    }
    if (max_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(8, max_);
    }
    if (unique_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(9, unique_);
    }
    if (!getTopBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(10, top_);
    }
    if (freq_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(11, freq_);
    }
    if (!getTypeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(12, type_);
    }
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(13, name_);
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
    if (!(obj instanceof kr.wise.advisor.prepare.summary.grpc.Summary)) {
      return super.equals(obj);
    }
    kr.wise.advisor.prepare.summary.grpc.Summary other = (kr.wise.advisor.prepare.summary.grpc.Summary) obj;

    boolean result = true;
    result = result && getName()
        .equals(other.getName());
    result = result && (getCount()
        == other.getCount());
    result = result && (
        java.lang.Float.floatToIntBits(getMean())
        == java.lang.Float.floatToIntBits(
            other.getMean()));
    result = result && (
        java.lang.Float.floatToIntBits(getMin())
        == java.lang.Float.floatToIntBits(
            other.getMin()));
    result = result && (
        java.lang.Float.floatToIntBits(getStd())
        == java.lang.Float.floatToIntBits(
            other.getStd()));
    result = result && (
        java.lang.Float.floatToIntBits(getQ1())
        == java.lang.Float.floatToIntBits(
            other.getQ1()));
    result = result && (
        java.lang.Float.floatToIntBits(getQ2())
        == java.lang.Float.floatToIntBits(
            other.getQ2()));
    result = result && (
        java.lang.Float.floatToIntBits(getQ3())
        == java.lang.Float.floatToIntBits(
            other.getQ3()));
    result = result && (
        java.lang.Float.floatToIntBits(getMax())
        == java.lang.Float.floatToIntBits(
            other.getMax()));
    result = result && (getUnique()
        == other.getUnique());
    result = result && getTop()
        .equals(other.getTop());
    result = result && (getFreq()
        == other.getFreq());
    result = result && getType()
        .equals(other.getType());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (37 * hash) + COUNT_FIELD_NUMBER;
    hash = (53 * hash) + getCount();
    hash = (37 * hash) + MEAN_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getMean());
    hash = (37 * hash) + MIN_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getMin());
    hash = (37 * hash) + STD_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getStd());
    hash = (37 * hash) + Q1_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getQ1());
    hash = (37 * hash) + Q2_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getQ2());
    hash = (37 * hash) + Q3_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getQ3());
    hash = (37 * hash) + MAX_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getMax());
    hash = (37 * hash) + UNIQUE_FIELD_NUMBER;
    hash = (53 * hash) + getUnique();
    hash = (37 * hash) + TOP_FIELD_NUMBER;
    hash = (53 * hash) + getTop().hashCode();
    hash = (37 * hash) + FREQ_FIELD_NUMBER;
    hash = (53 * hash) + getFreq();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + getType().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kr.wise.advisor.prepare.summary.grpc.Summary parseFrom(
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
  public static Builder newBuilder(kr.wise.advisor.prepare.summary.grpc.Summary prototype) {
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
   * <pre>
   *summary 응답...
   * </pre>
   *
   * Protobuf type {@code kr.wise.advisor.prepare.summary.Summary}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kr.wise.advisor.prepare.summary.Summary)
      kr.wise.advisor.prepare.summary.grpc.SummaryOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return kr.wise.advisor.prepare.summary.grpc.SummaryProcess.internal_static_kr_wise_advisor_prepare_summary_Summary_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return kr.wise.advisor.prepare.summary.grpc.SummaryProcess.internal_static_kr_wise_advisor_prepare_summary_Summary_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              kr.wise.advisor.prepare.summary.grpc.Summary.class, kr.wise.advisor.prepare.summary.grpc.Summary.Builder.class);
    }

    // Construct using kr.wise.advisor.prepare.summary.grpc.Summary.newBuilder()
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
      name_ = "";

      count_ = 0;

      mean_ = 0F;

      min_ = 0F;

      std_ = 0F;

      q1_ = 0F;

      q2_ = 0F;

      q3_ = 0F;

      max_ = 0F;

      unique_ = 0;

      top_ = "";

      freq_ = 0;

      type_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return kr.wise.advisor.prepare.summary.grpc.SummaryProcess.internal_static_kr_wise_advisor_prepare_summary_Summary_descriptor;
    }

    public kr.wise.advisor.prepare.summary.grpc.Summary getDefaultInstanceForType() {
      return kr.wise.advisor.prepare.summary.grpc.Summary.getDefaultInstance();
    }

    public kr.wise.advisor.prepare.summary.grpc.Summary build() {
      kr.wise.advisor.prepare.summary.grpc.Summary result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public kr.wise.advisor.prepare.summary.grpc.Summary buildPartial() {
      kr.wise.advisor.prepare.summary.grpc.Summary result = new kr.wise.advisor.prepare.summary.grpc.Summary(this);
      result.name_ = name_;
      result.count_ = count_;
      result.mean_ = mean_;
      result.min_ = min_;
      result.std_ = std_;
      result.q1_ = q1_;
      result.q2_ = q2_;
      result.q3_ = q3_;
      result.max_ = max_;
      result.unique_ = unique_;
      result.top_ = top_;
      result.freq_ = freq_;
      result.type_ = type_;
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
      if (other instanceof kr.wise.advisor.prepare.summary.grpc.Summary) {
        return mergeFrom((kr.wise.advisor.prepare.summary.grpc.Summary)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(kr.wise.advisor.prepare.summary.grpc.Summary other) {
      if (other == kr.wise.advisor.prepare.summary.grpc.Summary.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (other.getCount() != 0) {
        setCount(other.getCount());
      }
      if (other.getMean() != 0F) {
        setMean(other.getMean());
      }
      if (other.getMin() != 0F) {
        setMin(other.getMin());
      }
      if (other.getStd() != 0F) {
        setStd(other.getStd());
      }
      if (other.getQ1() != 0F) {
        setQ1(other.getQ1());
      }
      if (other.getQ2() != 0F) {
        setQ2(other.getQ2());
      }
      if (other.getQ3() != 0F) {
        setQ3(other.getQ3());
      }
      if (other.getMax() != 0F) {
        setMax(other.getMax());
      }
      if (other.getUnique() != 0) {
        setUnique(other.getUnique());
      }
      if (!other.getTop().isEmpty()) {
        top_ = other.top_;
        onChanged();
      }
      if (other.getFreq() != 0) {
        setFreq(other.getFreq());
      }
      if (!other.getType().isEmpty()) {
        type_ = other.type_;
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
      kr.wise.advisor.prepare.summary.grpc.Summary parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (kr.wise.advisor.prepare.summary.grpc.Summary) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object name_ = "";
    /**
     * <pre>
     *컬럼명
     * </pre>
     *
     * <code>string name = 13;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *컬럼명
     * </pre>
     *
     * <code>string name = 13;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *컬럼명
     * </pre>
     *
     * <code>string name = 13;</code>
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *컬럼명
     * </pre>
     *
     * <code>string name = 13;</code>
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *컬럼명
     * </pre>
     *
     * <code>string name = 13;</code>
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private int count_ ;
    /**
     * <pre>
     *전체수
     * </pre>
     *
     * <code>uint32 count = 1;</code>
     */
    public int getCount() {
      return count_;
    }
    /**
     * <pre>
     *전체수
     * </pre>
     *
     * <code>uint32 count = 1;</code>
     */
    public Builder setCount(int value) {
      
      count_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *전체수
     * </pre>
     *
     * <code>uint32 count = 1;</code>
     */
    public Builder clearCount() {
      
      count_ = 0;
      onChanged();
      return this;
    }

    private float mean_ ;
    /**
     * <pre>
     *평균
     * </pre>
     *
     * <code>float mean = 2;</code>
     */
    public float getMean() {
      return mean_;
    }
    /**
     * <pre>
     *평균
     * </pre>
     *
     * <code>float mean = 2;</code>
     */
    public Builder setMean(float value) {
      
      mean_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *평균
     * </pre>
     *
     * <code>float mean = 2;</code>
     */
    public Builder clearMean() {
      
      mean_ = 0F;
      onChanged();
      return this;
    }

    private float min_ ;
    /**
     * <pre>
     *최소값
     * </pre>
     *
     * <code>float min = 3;</code>
     */
    public float getMin() {
      return min_;
    }
    /**
     * <pre>
     *최소값
     * </pre>
     *
     * <code>float min = 3;</code>
     */
    public Builder setMin(float value) {
      
      min_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *최소값
     * </pre>
     *
     * <code>float min = 3;</code>
     */
    public Builder clearMin() {
      
      min_ = 0F;
      onChanged();
      return this;
    }

    private float std_ ;
    /**
     * <pre>
     *표준편차
     * </pre>
     *
     * <code>float std = 4;</code>
     */
    public float getStd() {
      return std_;
    }
    /**
     * <pre>
     *표준편차
     * </pre>
     *
     * <code>float std = 4;</code>
     */
    public Builder setStd(float value) {
      
      std_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *표준편차
     * </pre>
     *
     * <code>float std = 4;</code>
     */
    public Builder clearStd() {
      
      std_ = 0F;
      onChanged();
      return this;
    }

    private float q1_ ;
    /**
     * <pre>
     *1분위수
     * </pre>
     *
     * <code>float q1 = 5;</code>
     */
    public float getQ1() {
      return q1_;
    }
    /**
     * <pre>
     *1분위수
     * </pre>
     *
     * <code>float q1 = 5;</code>
     */
    public Builder setQ1(float value) {
      
      q1_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *1분위수
     * </pre>
     *
     * <code>float q1 = 5;</code>
     */
    public Builder clearQ1() {
      
      q1_ = 0F;
      onChanged();
      return this;
    }

    private float q2_ ;
    /**
     * <pre>
     *2분위수(중앙값)
     * </pre>
     *
     * <code>float q2 = 6;</code>
     */
    public float getQ2() {
      return q2_;
    }
    /**
     * <pre>
     *2분위수(중앙값)
     * </pre>
     *
     * <code>float q2 = 6;</code>
     */
    public Builder setQ2(float value) {
      
      q2_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *2분위수(중앙값)
     * </pre>
     *
     * <code>float q2 = 6;</code>
     */
    public Builder clearQ2() {
      
      q2_ = 0F;
      onChanged();
      return this;
    }

    private float q3_ ;
    /**
     * <pre>
     *3분위수
     * </pre>
     *
     * <code>float q3 = 7;</code>
     */
    public float getQ3() {
      return q3_;
    }
    /**
     * <pre>
     *3분위수
     * </pre>
     *
     * <code>float q3 = 7;</code>
     */
    public Builder setQ3(float value) {
      
      q3_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *3분위수
     * </pre>
     *
     * <code>float q3 = 7;</code>
     */
    public Builder clearQ3() {
      
      q3_ = 0F;
      onChanged();
      return this;
    }

    private float max_ ;
    /**
     * <pre>
     *최대값
     * </pre>
     *
     * <code>float max = 8;</code>
     */
    public float getMax() {
      return max_;
    }
    /**
     * <pre>
     *최대값
     * </pre>
     *
     * <code>float max = 8;</code>
     */
    public Builder setMax(float value) {
      
      max_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *최대값
     * </pre>
     *
     * <code>float max = 8;</code>
     */
    public Builder clearMax() {
      
      max_ = 0F;
      onChanged();
      return this;
    }

    private int unique_ ;
    /**
     * <pre>
     *유니크수
     * </pre>
     *
     * <code>uint32 unique = 9;</code>
     */
    public int getUnique() {
      return unique_;
    }
    /**
     * <pre>
     *유니크수
     * </pre>
     *
     * <code>uint32 unique = 9;</code>
     */
    public Builder setUnique(int value) {
      
      unique_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *유니크수
     * </pre>
     *
     * <code>uint32 unique = 9;</code>
     */
    public Builder clearUnique() {
      
      unique_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object top_ = "";
    /**
     * <pre>
     *최빈값
     * </pre>
     *
     * <code>string top = 10;</code>
     */
    public java.lang.String getTop() {
      java.lang.Object ref = top_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        top_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *최빈값
     * </pre>
     *
     * <code>string top = 10;</code>
     */
    public com.google.protobuf.ByteString
        getTopBytes() {
      java.lang.Object ref = top_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        top_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *최빈값
     * </pre>
     *
     * <code>string top = 10;</code>
     */
    public Builder setTop(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      top_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *최빈값
     * </pre>
     *
     * <code>string top = 10;</code>
     */
    public Builder clearTop() {
      
      top_ = getDefaultInstance().getTop();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *최빈값
     * </pre>
     *
     * <code>string top = 10;</code>
     */
    public Builder setTopBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      top_ = value;
      onChanged();
      return this;
    }

    private int freq_ ;
    /**
     * <pre>
     *최빈값 갯수
     * </pre>
     *
     * <code>uint32 freq = 11;</code>
     */
    public int getFreq() {
      return freq_;
    }
    /**
     * <pre>
     *최빈값 갯수
     * </pre>
     *
     * <code>uint32 freq = 11;</code>
     */
    public Builder setFreq(int value) {
      
      freq_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *최빈값 갯수
     * </pre>
     *
     * <code>uint32 freq = 11;</code>
     */
    public Builder clearFreq() {
      
      freq_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object type_ = "";
    /**
     * <pre>
     *변수타입
     * </pre>
     *
     * <code>string type = 12;</code>
     */
    public java.lang.String getType() {
      java.lang.Object ref = type_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        type_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *변수타입
     * </pre>
     *
     * <code>string type = 12;</code>
     */
    public com.google.protobuf.ByteString
        getTypeBytes() {
      java.lang.Object ref = type_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        type_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *변수타입
     * </pre>
     *
     * <code>string type = 12;</code>
     */
    public Builder setType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *변수타입
     * </pre>
     *
     * <code>string type = 12;</code>
     */
    public Builder clearType() {
      
      type_ = getDefaultInstance().getType();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *변수타입
     * </pre>
     *
     * <code>string type = 12;</code>
     */
    public Builder setTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      type_ = value;
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


    // @@protoc_insertion_point(builder_scope:kr.wise.advisor.prepare.summary.Summary)
  }

  // @@protoc_insertion_point(class_scope:kr.wise.advisor.prepare.summary.Summary)
  private static final kr.wise.advisor.prepare.summary.grpc.Summary DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new kr.wise.advisor.prepare.summary.grpc.Summary();
  }

  public static kr.wise.advisor.prepare.summary.grpc.Summary getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Summary>
      PARSER = new com.google.protobuf.AbstractParser<Summary>() {
    public Summary parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Summary(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Summary> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Summary> getParserForType() {
    return PARSER;
  }

  public kr.wise.advisor.prepare.summary.grpc.Summary getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

