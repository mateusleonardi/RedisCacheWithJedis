# Exemplos de uso:

## Limpando o cache
RedisCache.getInstance().cleanCache();

## Obtendo do cache
String key = "Identificação_única_dentro_do_Redis";
RedisCache.getInstance().getFromCache(key);

## Verificação se item existe no cache
String key = "Identificação_única_dentro_do_Redis";
RedisCache.getInstance().existsOnCache(key);

## Como colocar no cache utilizando o tempo padrão para expirar
String key = "Identificação_única_dentro_do_Redis";
String objectSerialized = "any object serialized";
RedisCache.getInstance().putOnCacheWithExpirationTime(key, objectSerialized, true);

## Como colocar no cache utilizando sem prazo para expirar
String key = "Identificação_única_dentro_do_Redis";
String objectSerialized = "any object serialized";
RedisCache.getInstance().putOnCacheWithExpirationTime(key, objectSerialized, false);


## Como colocar no cache informando tempo de expiração
String key = "Identificação_única_dentro_do_Redis";
String objectSerialized = "any object serialized";
RedisCache.getInstance().putOnCacheWithExpirationTime(key, objectSerialized, 5);