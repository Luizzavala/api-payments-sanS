package me.quadradev.application.core.model;

import lombok.Getter;

/**
 * Bitmask based permissions for menus.
 */
@Getter
public enum MenuAction {
    VIEW(1 << 0),
    CREATE(1 << 1),
    UPDATE(1 << 2),
    DELETE(1 << 3),
    EXPORT(1 << 4),
    APPROVE(1 << 5),
    MANAGE(1 << 6);

    private final int mask;

    MenuAction(int mask) {
        this.mask = mask;
    }

    public static int toMask(Iterable<MenuAction> actions) {
        int result = 0;
        for (MenuAction action : actions) {
            result |= action.getMask();
        }
        return result;
    }

    public static boolean hasAction(int mask, MenuAction action) {
        return (mask & action.getMask()) != 0;
    }
}
