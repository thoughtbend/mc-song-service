package com.thoughtbend.mc.songsvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtbend.mc.songsvc.resource.ArtistResource;

@RestController
@RequestMapping("/artist")
@CrossOrigin
public class ArtistController {

	private final static List<ArtistResource> ARTIST_LIST = new ArrayList<>();
	static {
		final ArtistResource artist1 = new ArtistResource();
		artist1.setId(UUID.randomUUID().toString());
		artist1.setName("The Airborne Toxic Event");
		ARTIST_LIST.add(artist1);
		
		final ArtistResource artist2 = new ArtistResource();
		artist2.setId(UUID.randomUUID().toString());
		artist2.setName("Soundgarden");
		ARTIST_LIST.add(artist2);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('PERMISSION_read:artists')")
	//@PreAuthorize("principal?.claims['permissions'].contains('read:artists')")
	@ResponseBody
	public List<ArtistResource> findArtists(Authentication auth) {
		
		
		return ARTIST_LIST;
	}
}
