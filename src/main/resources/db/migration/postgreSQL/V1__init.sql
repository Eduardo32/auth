
CREATE TABLE public.funcionalidades (
	id bigserial NOT NULL,
	data_atualizacao timestamp NOT NULL,
	data_criacao timestamp NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT funcionalidades_pkey PRIMARY KEY (id),
	CONSTRAINT uk_3m1ascfbv7dx8qrvfs2pmjiyq UNIQUE (nome)
);

CREATE TABLE public.perfis (
	id bigserial NOT NULL,
	data_atualizacao timestamp NOT NULL,
	data_criacao timestamp NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT perfis_pkey PRIMARY KEY (id),
	CONSTRAINT uk_10p6gqv0q50bo17oxgx9p29il UNIQUE (nome)
);

CREATE TABLE public.perfil_funcionalidade (
	perfil_id int8 NOT NULL,
	funcionalidade_id int8 NOT NULL
);


-- public.perfil_funcionalidade foreign keys

ALTER TABLE public.perfil_funcionalidade ADD CONSTRAINT fk1ydfadgleyih7iod943c5gaxd FOREIGN KEY (perfil_id) REFERENCES public.perfis(id);
ALTER TABLE public.perfil_funcionalidade ADD CONSTRAINT fkqar22e15uyp6qc9phrxk4go51 FOREIGN KEY (funcionalidade_id) REFERENCES public.funcionalidades(id);

CREATE TABLE public.usuarios (
	id bigserial NOT NULL,
	data_atualizacao timestamp NOT NULL,
	data_criacao timestamp NOT NULL,
	login varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	uuid varchar(255) NOT NULL,
	CONSTRAINT uk_r8oo98o39ykr4hi57md9nibmw UNIQUE (login),
	CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);

CREATE TABLE public.usuario_perfil (
	usuario_id int8 NOT NULL,
	perfil_id int8 NOT NULL
);


-- public.usuario_perfil foreign keys

ALTER TABLE public.usuario_perfil ADD CONSTRAINT fk46v67aocrgergt30mf76suhgm FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id);
ALTER TABLE public.usuario_perfil ADD CONSTRAINT fkfc3bg1xm20dbaj4tevlsy289f FOREIGN KEY (perfil_id) REFERENCES public.perfis(id);
