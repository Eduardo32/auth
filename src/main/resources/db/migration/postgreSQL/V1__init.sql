CREATE TABLE public.perfis (
	id bigserial NOT NULL,
	nome varchar(255) NULL,
	CONSTRAINT perfis_pkey PRIMARY KEY (id)
);

CREATE TABLE public.usuarios (
	id bigserial NOT NULL,
	data_atualizacao timestamp NOT NULL,
	data_criacao timestamp NOT NULL,
	email varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	uuid varchar(255) NOT NULL,
	CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);

CREATE TABLE public.usuarios_perfis (
	usuario_id int8 NOT NULL,
	perfis_id int8 NOT NULL
);


-- public.usuarios_perfis foreign keys

ALTER TABLE public.usuarios_perfis ADD CONSTRAINT fkcvq90lk95py1n889uimu18atx FOREIGN KEY (perfis_id) REFERENCES public.perfis(id);
ALTER TABLE public.usuarios_perfis ADD CONSTRAINT fkotpfkn8c9nmhqqy4pb8hkgp18 FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id);
