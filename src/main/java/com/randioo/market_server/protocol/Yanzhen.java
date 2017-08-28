// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Yanzhen.proto

package com.randioo.market_server.protocol;

public final class Yanzhen {
  private Yanzhen() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class YanzhenRequest extends
      com.google.protobuf.GeneratedMessage {
    // Use YanzhenRequest.newBuilder() to construct.
    private YanzhenRequest() {
      initFields();
    }
    private YanzhenRequest(boolean noInit) {}
    
    private static final YanzhenRequest defaultInstance;
    public static YanzhenRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public YanzhenRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.randioo.market_server.protocol.Yanzhen.internal_static_com_randioo_market_server_protocol_YanzhenRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.randioo.market_server.protocol.Yanzhen.internal_static_com_randioo_market_server_protocol_YanzhenRequest_fieldAccessorTable;
    }
    
    // optional string yanzhen = 1;
    public static final int YANZHEN_FIELD_NUMBER = 1;
    private boolean hasYanzhen;
    private java.lang.String yanzhen_ = "";
    public boolean hasYanzhen() { return hasYanzhen; }
    public java.lang.String getYanzhen() { return yanzhen_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasYanzhen()) {
        output.writeString(1, getYanzhen());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasYanzhen()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getYanzhen());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.randioo.market_server.protocol.Yanzhen.YanzhenRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.randioo.market_server.protocol.Yanzhen.YanzhenRequest result;
      
      // Construct using com.randioo.market_server.protocol.Yanzhen.YanzhenRequest.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.randioo.market_server.protocol.Yanzhen.YanzhenRequest();
        return builder;
      }
      
      protected com.randioo.market_server.protocol.Yanzhen.YanzhenRequest internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.randioo.market_server.protocol.Yanzhen.YanzhenRequest();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.randioo.market_server.protocol.Yanzhen.YanzhenRequest.getDescriptor();
      }
      
      public com.randioo.market_server.protocol.Yanzhen.YanzhenRequest getDefaultInstanceForType() {
        return com.randioo.market_server.protocol.Yanzhen.YanzhenRequest.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.randioo.market_server.protocol.Yanzhen.YanzhenRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.randioo.market_server.protocol.Yanzhen.YanzhenRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.randioo.market_server.protocol.Yanzhen.YanzhenRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.randioo.market_server.protocol.Yanzhen.YanzhenRequest returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.randioo.market_server.protocol.Yanzhen.YanzhenRequest) {
          return mergeFrom((com.randioo.market_server.protocol.Yanzhen.YanzhenRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.randioo.market_server.protocol.Yanzhen.YanzhenRequest other) {
        if (other == com.randioo.market_server.protocol.Yanzhen.YanzhenRequest.getDefaultInstance()) return this;
        if (other.hasYanzhen()) {
          setYanzhen(other.getYanzhen());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setYanzhen(input.readString());
              break;
            }
          }
        }
      }
      
      
      // optional string yanzhen = 1;
      public boolean hasYanzhen() {
        return result.hasYanzhen();
      }
      public java.lang.String getYanzhen() {
        return result.getYanzhen();
      }
      public Builder setYanzhen(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasYanzhen = true;
        result.yanzhen_ = value;
        return this;
      }
      public Builder clearYanzhen() {
        result.hasYanzhen = false;
        result.yanzhen_ = getDefaultInstance().getYanzhen();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.randioo.market_server.protocol.YanzhenRequest)
    }
    
    static {
      defaultInstance = new YanzhenRequest(true);
      com.randioo.market_server.protocol.Yanzhen.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.randioo.market_server.protocol.YanzhenRequest)
  }
  
  public static final class YanzhenResponse extends
      com.google.protobuf.GeneratedMessage {
    // Use YanzhenResponse.newBuilder() to construct.
    private YanzhenResponse() {
      initFields();
    }
    private YanzhenResponse(boolean noInit) {}
    
    private static final YanzhenResponse defaultInstance;
    public static YanzhenResponse getDefaultInstance() {
      return defaultInstance;
    }
    
    public YanzhenResponse getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.randioo.market_server.protocol.Yanzhen.internal_static_com_randioo_market_server_protocol_YanzhenResponse_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.randioo.market_server.protocol.Yanzhen.internal_static_com_randioo_market_server_protocol_YanzhenResponse_fieldAccessorTable;
    }
    
    // optional int32 errorCode = 1 [default = 1];
    public static final int ERRORCODE_FIELD_NUMBER = 1;
    private boolean hasErrorCode;
    private int errorCode_ = 1;
    public boolean hasErrorCode() { return hasErrorCode; }
    public int getErrorCode() { return errorCode_; }
    
    // optional string yanzhen = 2;
    public static final int YANZHEN_FIELD_NUMBER = 2;
    private boolean hasYanzhen;
    private java.lang.String yanzhen_ = "";
    public boolean hasYanzhen() { return hasYanzhen; }
    public java.lang.String getYanzhen() { return yanzhen_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasErrorCode()) {
        output.writeInt32(1, getErrorCode());
      }
      if (hasYanzhen()) {
        output.writeString(2, getYanzhen());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasErrorCode()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getErrorCode());
      }
      if (hasYanzhen()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getYanzhen());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Yanzhen.YanzhenResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.randioo.market_server.protocol.Yanzhen.YanzhenResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.randioo.market_server.protocol.Yanzhen.YanzhenResponse result;
      
      // Construct using com.randioo.market_server.protocol.Yanzhen.YanzhenResponse.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.randioo.market_server.protocol.Yanzhen.YanzhenResponse();
        return builder;
      }
      
      protected com.randioo.market_server.protocol.Yanzhen.YanzhenResponse internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.randioo.market_server.protocol.Yanzhen.YanzhenResponse();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.randioo.market_server.protocol.Yanzhen.YanzhenResponse.getDescriptor();
      }
      
      public com.randioo.market_server.protocol.Yanzhen.YanzhenResponse getDefaultInstanceForType() {
        return com.randioo.market_server.protocol.Yanzhen.YanzhenResponse.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.randioo.market_server.protocol.Yanzhen.YanzhenResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.randioo.market_server.protocol.Yanzhen.YanzhenResponse buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.randioo.market_server.protocol.Yanzhen.YanzhenResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.randioo.market_server.protocol.Yanzhen.YanzhenResponse returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.randioo.market_server.protocol.Yanzhen.YanzhenResponse) {
          return mergeFrom((com.randioo.market_server.protocol.Yanzhen.YanzhenResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.randioo.market_server.protocol.Yanzhen.YanzhenResponse other) {
        if (other == com.randioo.market_server.protocol.Yanzhen.YanzhenResponse.getDefaultInstance()) return this;
        if (other.hasErrorCode()) {
          setErrorCode(other.getErrorCode());
        }
        if (other.hasYanzhen()) {
          setYanzhen(other.getYanzhen());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setErrorCode(input.readInt32());
              break;
            }
            case 18: {
              setYanzhen(input.readString());
              break;
            }
          }
        }
      }
      
      
      // optional int32 errorCode = 1 [default = 1];
      public boolean hasErrorCode() {
        return result.hasErrorCode();
      }
      public int getErrorCode() {
        return result.getErrorCode();
      }
      public Builder setErrorCode(int value) {
        result.hasErrorCode = true;
        result.errorCode_ = value;
        return this;
      }
      public Builder clearErrorCode() {
        result.hasErrorCode = false;
        result.errorCode_ = 1;
        return this;
      }
      
      // optional string yanzhen = 2;
      public boolean hasYanzhen() {
        return result.hasYanzhen();
      }
      public java.lang.String getYanzhen() {
        return result.getYanzhen();
      }
      public Builder setYanzhen(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasYanzhen = true;
        result.yanzhen_ = value;
        return this;
      }
      public Builder clearYanzhen() {
        result.hasYanzhen = false;
        result.yanzhen_ = getDefaultInstance().getYanzhen();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.randioo.market_server.protocol.YanzhenResponse)
    }
    
    static {
      defaultInstance = new YanzhenResponse(true);
      com.randioo.market_server.protocol.Yanzhen.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.randioo.market_server.protocol.YanzhenResponse)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_randioo_market_server_protocol_YanzhenRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_randioo_market_server_protocol_YanzhenRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_randioo_market_server_protocol_YanzhenResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_randioo_market_server_protocol_YanzhenResponse_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rYanzhen.proto\022\"com.randioo.market_serv" +
      "er.protocol\032\014Entity.proto\"!\n\016YanzhenRequ" +
      "est\022\017\n\007yanzhen\030\001 \001(\t\"8\n\017YanzhenResponse\022" +
      "\024\n\terrorCode\030\001 \001(\005:\0011\022\017\n\007yanzhen\030\002 \001(\t"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_randioo_market_server_protocol_YanzhenRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_randioo_market_server_protocol_YanzhenRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_randioo_market_server_protocol_YanzhenRequest_descriptor,
              new java.lang.String[] { "Yanzhen", },
              com.randioo.market_server.protocol.Yanzhen.YanzhenRequest.class,
              com.randioo.market_server.protocol.Yanzhen.YanzhenRequest.Builder.class);
          internal_static_com_randioo_market_server_protocol_YanzhenResponse_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_randioo_market_server_protocol_YanzhenResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_randioo_market_server_protocol_YanzhenResponse_descriptor,
              new java.lang.String[] { "ErrorCode", "Yanzhen", },
              com.randioo.market_server.protocol.Yanzhen.YanzhenResponse.class,
              com.randioo.market_server.protocol.Yanzhen.YanzhenResponse.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.randioo.market_server.protocol.Entity.getDescriptor(),
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}
