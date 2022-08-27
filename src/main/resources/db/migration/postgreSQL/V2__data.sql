
INSERT INTO perfis(nome, data_criacao, data_atualizacao)
VALUES('ADMINISTRADOR', NOW(), NOW()),
    ('USUARIO', NOW(), NOW());

INSERT INTO funcionalidades(nome, data_criacao, data_atualizacao)
VALUES('ADMIN', NOW(), NOW()),
    ('LISTAR_FUNCIONALIDADE', NOW(), NOW()),
    ('CRIAR_FUNCIONALIDADE', NOW(), NOW()),
    ('ATUALIZAR_FUNCIONALIDADE', NOW(), NOW()),
    ('EXCLUIR_FUNCIONALIDADE', NOW(), NOW()),
    ('LISTAR_PERFIL', NOW(), NOW()),
    ('CRIAR_PERFIL', NOW(), NOW()),
    ('ATUALIZAR_PERFIL', NOW(), NOW()),
    ('EXCLUIR_PERFIL', NOW(), NOW()),
    ('LISTAR_USUARIO', NOW(), NOW()),
    ('CRIAR_USUARIO', NOW(), NOW()),
    ('ATUALIZAR_USUARIO', NOW(), NOW()),
    ('EXCLUIR_USUARIO', NOW(), NOW());

INSERT INTO perfil_funcionalidade(perfil_id, funcionalidade_id) VALUES(1, 1);

INSERT INTO usuarios(login, senha, uuid, data_criacao, data_atualizacao)
VALUES('admin', '$2a$10$WDrDVuiwbFUXeLeoW/ExqOZ5IAsL80EYzgB4Xjpfv7H9A45zmyH/S', '1c7916ca-2afa-4fbd-a15d-94a3fe3ac050', NOW(), NOW());

INSERT INTO usuario_perfil(usuario_id, perfil_id) VALUES(1, 1);
