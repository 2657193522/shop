// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Goods.proto

package com.randioo.market_server.protocol;

public final class Goods {
  private Goods() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class GoodsRequest extends
      com.google.protobuf.GeneratedMessage {
    // Use GoodsRequest.newBuilder() to construct.
    private GoodsRequest() {
      initFields();
    }
    private GoodsRequest(boolean noInit) {}
    
    private static final GoodsRequest defaultInstance;
    public static GoodsRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public GoodsRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.randioo.market_server.protocol.Goods.internal_static_com_randioo_market_server_protocol_GoodsRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.randioo.market_server.protocol.Goods.internal_static_com_randioo_market_server_protocol_GoodsRequest_fieldAccessorTable;
    }
    
    // optional string type = 1;
    public static final int TYPE_FIELD_NUMBER = 1;
    private boolean hasType;
    private java.lang.String type_ = "";
    public boolean hasType() { return hasType; }
    public java.lang.String getType() { return type_; }
    
    // optional string price = 2;
    public static final int PRICE_FIELD_NUMBER = 2;
    private boolean hasPrice;
    private java.lang.String price_ = "";
    public boolean hasPrice() { return hasPrice; }
    public java.lang.String getPrice() { return price_; }
    
    // optional string sellAccount = 3;
    public static final int SELLACCOUNT_FIELD_NUMBER = 3;
    private boolean hasSellAccount;
    private java.lang.String sellAccount_ = "";
    public boolean hasSellAccount() { return hasSellAccount; }
    public java.lang.String getSellAccount() { return sellAccount_; }
    
    // optional int32 sellId = 4;
    public static final int SELLID_FIELD_NUMBER = 4;
    private boolean hasSellId;
    private int sellId_ = 0;
    public boolean hasSellId() { return hasSellId; }
    public int getSellId() { return sellId_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasType()) {
        output.writeString(1, getType());
      }
      if (hasPrice()) {
        output.writeString(2, getPrice());
      }
      if (hasSellAccount()) {
        output.writeString(3, getSellAccount());
      }
      if (hasSellId()) {
        output.writeInt32(4, getSellId());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasType()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getType());
      }
      if (hasPrice()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getPrice());
      }
      if (hasSellAccount()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(3, getSellAccount());
      }
      if (hasSellId()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, getSellId());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseDelimitedFrom(
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
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.randioo.market_server.protocol.Goods.GoodsRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.randioo.market_server.protocol.Goods.GoodsRequest result;
      
      // Construct using com.randioo.market_server.protocol.Goods.GoodsRequest.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.randioo.market_server.protocol.Goods.GoodsRequest();
        return builder;
      }
      
      protected com.randioo.market_server.protocol.Goods.GoodsRequest internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.randioo.market_server.protocol.Goods.GoodsRequest();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.randioo.market_server.protocol.Goods.GoodsRequest.getDescriptor();
      }
      
      public com.randioo.market_server.protocol.Goods.GoodsRequest getDefaultInstanceForType() {
        return com.randioo.market_server.protocol.Goods.GoodsRequest.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.randioo.market_server.protocol.Goods.GoodsRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.randioo.market_server.protocol.Goods.GoodsRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.randioo.market_server.protocol.Goods.GoodsRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.randioo.market_server.protocol.Goods.GoodsRequest returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.randioo.market_server.protocol.Goods.GoodsRequest) {
          return mergeFrom((com.randioo.market_server.protocol.Goods.GoodsRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.randioo.market_server.protocol.Goods.GoodsRequest other) {
        if (other == com.randioo.market_server.protocol.Goods.GoodsRequest.getDefaultInstance()) return this;
        if (other.hasType()) {
          setType(other.getType());
        }
        if (other.hasPrice()) {
          setPrice(other.getPrice());
        }
        if (other.hasSellAccount()) {
          setSellAccount(other.getSellAccount());
        }
        if (other.hasSellId()) {
          setSellId(other.getSellId());
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
              setType(input.readString());
              break;
            }
            case 18: {
              setPrice(input.readString());
              break;
            }
            case 26: {
              setSellAccount(input.readString());
              break;
            }
            case 32: {
              setSellId(input.readInt32());
              break;
            }
          }
        }
      }
      
      
      // optional string type = 1;
      public boolean hasType() {
        return result.hasType();
      }
      public java.lang.String getType() {
        return result.getType();
      }
      public Builder setType(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasType = true;
        result.type_ = value;
        return this;
      }
      public Builder clearType() {
        result.hasType = false;
        result.type_ = getDefaultInstance().getType();
        return this;
      }
      
      // optional string price = 2;
      public boolean hasPrice() {
        return result.hasPrice();
      }
      public java.lang.String getPrice() {
        return result.getPrice();
      }
      public Builder setPrice(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasPrice = true;
        result.price_ = value;
        return this;
      }
      public Builder clearPrice() {
        result.hasPrice = false;
        result.price_ = getDefaultInstance().getPrice();
        return this;
      }
      
      // optional string sellAccount = 3;
      public boolean hasSellAccount() {
        return result.hasSellAccount();
      }
      public java.lang.String getSellAccount() {
        return result.getSellAccount();
      }
      public Builder setSellAccount(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasSellAccount = true;
        result.sellAccount_ = value;
        return this;
      }
      public Builder clearSellAccount() {
        result.hasSellAccount = false;
        result.sellAccount_ = getDefaultInstance().getSellAccount();
        return this;
      }
      
      // optional int32 sellId = 4;
      public boolean hasSellId() {
        return result.hasSellId();
      }
      public int getSellId() {
        return result.getSellId();
      }
      public Builder setSellId(int value) {
        result.hasSellId = true;
        result.sellId_ = value;
        return this;
      }
      public Builder clearSellId() {
        result.hasSellId = false;
        result.sellId_ = 0;
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.randioo.market_server.protocol.GoodsRequest)
    }
    
    static {
      defaultInstance = new GoodsRequest(true);
      com.randioo.market_server.protocol.Goods.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.randioo.market_server.protocol.GoodsRequest)
  }
  
  public static final class GoodsResponse extends
      com.google.protobuf.GeneratedMessage {
    // Use GoodsResponse.newBuilder() to construct.
    private GoodsResponse() {
      initFields();
    }
    private GoodsResponse(boolean noInit) {}
    
    private static final GoodsResponse defaultInstance;
    public static GoodsResponse getDefaultInstance() {
      return defaultInstance;
    }
    
    public GoodsResponse getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.randioo.market_server.protocol.Goods.internal_static_com_randioo_market_server_protocol_GoodsResponse_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.randioo.market_server.protocol.Goods.internal_static_com_randioo_market_server_protocol_GoodsResponse_fieldAccessorTable;
    }
    
    // optional int32 errorCode = 1 [default = 1];
    public static final int ERRORCODE_FIELD_NUMBER = 1;
    private boolean hasErrorCode;
    private int errorCode_ = 1;
    public boolean hasErrorCode() { return hasErrorCode; }
    public int getErrorCode() { return errorCode_; }
    
    // repeated .com.randioo.market_server.protocol.GoodsData GoodsData = 2;
    public static final int GOODSDATA_FIELD_NUMBER = 2;
    private java.util.List<com.randioo.market_server.protocol.Entity.GoodsData> goodsData_ =
      java.util.Collections.emptyList();
    public java.util.List<com.randioo.market_server.protocol.Entity.GoodsData> getGoodsDataList() {
      return goodsData_;
    }
    public int getGoodsDataCount() { return goodsData_.size(); }
    public com.randioo.market_server.protocol.Entity.GoodsData getGoodsData(int index) {
      return goodsData_.get(index);
    }
    
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
      for (com.randioo.market_server.protocol.Entity.GoodsData element : getGoodsDataList()) {
        output.writeMessage(2, element);
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
      for (com.randioo.market_server.protocol.Entity.GoodsData element : getGoodsDataList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseDelimitedFrom(
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
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Goods.GoodsResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.randioo.market_server.protocol.Goods.GoodsResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.randioo.market_server.protocol.Goods.GoodsResponse result;
      
      // Construct using com.randioo.market_server.protocol.Goods.GoodsResponse.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.randioo.market_server.protocol.Goods.GoodsResponse();
        return builder;
      }
      
      protected com.randioo.market_server.protocol.Goods.GoodsResponse internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.randioo.market_server.protocol.Goods.GoodsResponse();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.randioo.market_server.protocol.Goods.GoodsResponse.getDescriptor();
      }
      
      public com.randioo.market_server.protocol.Goods.GoodsResponse getDefaultInstanceForType() {
        return com.randioo.market_server.protocol.Goods.GoodsResponse.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.randioo.market_server.protocol.Goods.GoodsResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.randioo.market_server.protocol.Goods.GoodsResponse buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.randioo.market_server.protocol.Goods.GoodsResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        if (result.goodsData_ != java.util.Collections.EMPTY_LIST) {
          result.goodsData_ =
            java.util.Collections.unmodifiableList(result.goodsData_);
        }
        com.randioo.market_server.protocol.Goods.GoodsResponse returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.randioo.market_server.protocol.Goods.GoodsResponse) {
          return mergeFrom((com.randioo.market_server.protocol.Goods.GoodsResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.randioo.market_server.protocol.Goods.GoodsResponse other) {
        if (other == com.randioo.market_server.protocol.Goods.GoodsResponse.getDefaultInstance()) return this;
        if (other.hasErrorCode()) {
          setErrorCode(other.getErrorCode());
        }
        if (!other.goodsData_.isEmpty()) {
          if (result.goodsData_.isEmpty()) {
            result.goodsData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.GoodsData>();
          }
          result.goodsData_.addAll(other.goodsData_);
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
              com.randioo.market_server.protocol.Entity.GoodsData.Builder subBuilder = com.randioo.market_server.protocol.Entity.GoodsData.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addGoodsData(subBuilder.buildPartial());
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
      
      // repeated .com.randioo.market_server.protocol.GoodsData GoodsData = 2;
      public java.util.List<com.randioo.market_server.protocol.Entity.GoodsData> getGoodsDataList() {
        return java.util.Collections.unmodifiableList(result.goodsData_);
      }
      public int getGoodsDataCount() {
        return result.getGoodsDataCount();
      }
      public com.randioo.market_server.protocol.Entity.GoodsData getGoodsData(int index) {
        return result.getGoodsData(index);
      }
      public Builder setGoodsData(int index, com.randioo.market_server.protocol.Entity.GoodsData value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.goodsData_.set(index, value);
        return this;
      }
      public Builder setGoodsData(int index, com.randioo.market_server.protocol.Entity.GoodsData.Builder builderForValue) {
        result.goodsData_.set(index, builderForValue.build());
        return this;
      }
      public Builder addGoodsData(com.randioo.market_server.protocol.Entity.GoodsData value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.goodsData_.isEmpty()) {
          result.goodsData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.GoodsData>();
        }
        result.goodsData_.add(value);
        return this;
      }
      public Builder addGoodsData(com.randioo.market_server.protocol.Entity.GoodsData.Builder builderForValue) {
        if (result.goodsData_.isEmpty()) {
          result.goodsData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.GoodsData>();
        }
        result.goodsData_.add(builderForValue.build());
        return this;
      }
      public Builder addAllGoodsData(
          java.lang.Iterable<? extends com.randioo.market_server.protocol.Entity.GoodsData> values) {
        if (result.goodsData_.isEmpty()) {
          result.goodsData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.GoodsData>();
        }
        super.addAll(values, result.goodsData_);
        return this;
      }
      public Builder clearGoodsData() {
        result.goodsData_ = java.util.Collections.emptyList();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.randioo.market_server.protocol.GoodsResponse)
    }
    
    static {
      defaultInstance = new GoodsResponse(true);
      com.randioo.market_server.protocol.Goods.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.randioo.market_server.protocol.GoodsResponse)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_randioo_market_server_protocol_GoodsRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_randioo_market_server_protocol_GoodsRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_randioo_market_server_protocol_GoodsResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_randioo_market_server_protocol_GoodsResponse_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013Goods.proto\022\"com.randioo.market_server" +
      ".protocol\032\014Entity.proto\"P\n\014GoodsRequest\022" +
      "\014\n\004type\030\001 \001(\t\022\r\n\005price\030\002 \001(\t\022\023\n\013sellAcco" +
      "unt\030\003 \001(\t\022\016\n\006sellId\030\004 \001(\005\"g\n\rGoodsRespon" +
      "se\022\024\n\terrorCode\030\001 \001(\005:\0011\022@\n\tGoodsData\030\002 " +
      "\003(\0132-.com.randioo.market_server.protocol" +
      ".GoodsData"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_randioo_market_server_protocol_GoodsRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_randioo_market_server_protocol_GoodsRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_randioo_market_server_protocol_GoodsRequest_descriptor,
              new java.lang.String[] { "Type", "Price", "SellAccount", "SellId", },
              com.randioo.market_server.protocol.Goods.GoodsRequest.class,
              com.randioo.market_server.protocol.Goods.GoodsRequest.Builder.class);
          internal_static_com_randioo_market_server_protocol_GoodsResponse_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_randioo_market_server_protocol_GoodsResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_randioo_market_server_protocol_GoodsResponse_descriptor,
              new java.lang.String[] { "ErrorCode", "GoodsData", },
              com.randioo.market_server.protocol.Goods.GoodsResponse.class,
              com.randioo.market_server.protocol.Goods.GoodsResponse.Builder.class);
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
