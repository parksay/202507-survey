package org.parksay.core.entity;

public interface VersionCloneable {


    // 엔티티 버전을 관리할 때 나머지 값들은 그대로 둔 채로 버전 값만 하나씩 올려서 새로운 인스턴스를 제공하는 팩토리 메소드.
    // update 가 아니라 insert 쿼리가 나갈 수 있도록.
    public abstract BaseEntity cloneWithNewVersion(int newVersion);
}
