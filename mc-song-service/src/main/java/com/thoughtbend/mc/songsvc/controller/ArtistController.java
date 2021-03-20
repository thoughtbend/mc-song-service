package com.thoughtbend.mc.songsvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtbend.mc.songsvc.dataobject.Artist;
import com.thoughtbend.mc.songsvc.repository.ArtistCollectionRepository;
import com.thoughtbend.mc.songsvc.resource.ArtistResource;

@RestController
@RequestMapping("/artist")
@CrossOrigin
public class ArtistController {
	
	private final static Logger LOG = LoggerFactory.getLogger(ArtistController.class);
	
	@Autowired
	private ArtistCollectionRepository artistRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('PERMISSION_read:artists')")
	//@PreAuthorize("principal?.claims['permissions'].contains('read:artists')")
	@ResponseBody
	public List<ArtistResource> findArtists() {
		
		List<Artist> artistDataList = this.artistRepository.findAll();
		List<ArtistResource> artistResourceList = artistDataList.stream()
				.map(source -> {
					final ArtistResource target = new ArtistResource();
					target.setId(source.getDbId());
					target.setName(source.getName());
					return target;
				})
				.collect(Collectors.toList());
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("{} Artists retrieved", artistResourceList.size());
		}
		
		return artistResourceList;
	}
}
