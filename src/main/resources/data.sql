
INSERT INTO perfis(nome)
VALUES('ADMINISTRADOR'),
    ('USUARIO');

INSERT INTO funcionalidades(nome)
VALUES('CRIAR_USUARIO');

INSERT INTO perfis_funcionalidades(perfil_id, funcionalidades_id)
VALUES(1, 1),
    (2, 1);

/*
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('1d33c32f-9e6e-4d4b-8b0d-2d52ac623b9c', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('e5574f59-61c5-41e0-bc2c-70eef094404f', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('76deaaaf-3cd9-4797-a9f5-3c0034079209', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
INSERT INTO verificacao_email(uuid, data_criacao, data_verificacao, verificado) VALUES('1009ae18-2db9-487b-bade-622f6521b596', '2019-05-05 18:00:00', '2019-05-05 18:00:00', TRUE);
*/

INSERT INTO usuarios(login, senha, uuid, data_criacao, data_atualizacao)
VALUES('admin', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', '1c7916ca-2afa-4fbd-a15d-94a3fe3ac050', '2022-08-20 18:00:00', '2022-08-20 18:00:00'),
    ('usuario01', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', '2cb8b0b2-4876-42ed-85f6-4adfcc5191f9', '2022-08-20 18:00:00', '2022-08-20 18:00:00'),
    ('usuario02', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', 'fb85ddff-c4a0-42f6-b240-e4920ccbc230', '2022-08-20 18:00:00', '2022-08-20 18:00:00'),
    ('eu', '$2a$10$..BbYZDEPCujwGSDt.DUOOKFvj6WPJL23X4rHOUuO1Rsdr./oUjiC', 'd42923c3-7567-4898-a902-e6099a8bda8e', '2022-08-20 18:00:00', '2022-08-20 18:00:00');

INSERT INTO usuarios_perfis(usuario_id, perfis_id)
VALUES(1, 1),
    (2, 2),
    (3, 2),
    (4, 2);
