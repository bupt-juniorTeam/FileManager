package com.BUPTJuniorTeam.filemanager.accessobject;

/**
 * AccessObject工厂
 */
public class AccessObjectFactory {
    private static AccessObjectFactory instance = null;
    private AccessObjectFactory() {}

    /**
     * 获取工厂实例
     * @return AccessObjectFactory, 工厂唯一实例
     */
    public static AccessObjectFactory getInstance() {
        if (instance == null) {
            instance = new AccessObjectFactory();
        }

        return instance;
    }

    /**
     * 创建AccessObject
     * @param type AccessObject类型
     * @return IAccessObject实例
     */
    public IAccessObject createAccessObject(AccessType type) {
        IAccessObject object = null;
        switch (type) {
            case ACCESS_FTP:
            case ACCESS_HISTORY:
            case ACCESS_EXTERNAL:
                break;
            case ACCESS_INTERNAL:
                object = new InternalAccessObject();
                break;
        }

        return object;
    }
}
