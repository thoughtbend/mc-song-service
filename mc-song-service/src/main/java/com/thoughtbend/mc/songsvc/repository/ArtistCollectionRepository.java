package com.thoughtbend.mc.songsvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thoughtbend.mc.songsvc.dataobject.Artist;

public interface ArtistCollectionRepository extends MongoRepository<Artist, String> {

}
