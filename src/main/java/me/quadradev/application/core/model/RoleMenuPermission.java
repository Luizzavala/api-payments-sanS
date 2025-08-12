package me.quadradev.application.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "core_role_menu_permissions",
        uniqueConstraints = @UniqueConstraint(name = "uk_role_menu", columnNames = {"role_id", "menu_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleMenuPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    /**
     * Bitmask of {@link MenuAction} permissions.
     */
    @Column(nullable = false)
    private int permissions;
}
