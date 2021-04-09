package com.thoughtbend.mc.songsvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtbend.mc.songsvc.dataobject.Artist;
import com.thoughtbend.mc.songsvc.exception.NotFoundException;
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
		
		List<Artist> artistDataList = this.artistRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		
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
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String saveNewArtist(@RequestBody ArtistResource resource) {
		
		Artist data = new Artist();
		
		data.setId(Long.valueOf(resource.getId()));
		data.setName(resource.getName());
		
		final Artist postSaveData = this.artistRepository.save(data);
		
		return "Saved with key - " + postSaveData.getDbId();
	}
	
	@GetMapping(path = "/{artistId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('PERMISSION_read:artists')")
	@ResponseBody
	public ArtistResource getArtistById(@PathVariable(name = "artistId") final String artistId) {
		
		final Optional<Artist> artistDataHolder = this.artistRepository.findById(artistId);
		
		if (artistDataHolder.isPresent()) {
			final Artist artistData = artistDataHolder.get();
			final ArtistResource artistResource = new ArtistResource();
			artistResource.setId(artistData.getDbId());
			artistResource.setName(artistData.getName());
			
			return artistResource;
		}
		else {
			throw new NotFoundException();
		}
	}
	
	@DeleteMapping(path = "/{artistId}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deleteArtistById(@PathVariable(name = "artistId") final String artistId) {
		
		if (!this.artistRepository.existsById(artistId)) {
			throw new NotFoundException();
		}
		
		this.artistRepository.deleteById(artistId);
	}
	
	@PutMapping(path = "/{artistId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void updateArtistById(@PathVariable(name = "artistId") final String artistId, @RequestBody(required = true) final ArtistResource artistResource) {
		
		final Optional<Artist> artistDataHolder = this.artistRepository.findById(artistId);
		
		if (artistDataHolder.isPresent()) {
			final Artist artistData = artistDataHolder.get();

			artistData.setName(artistResource.getName());
			
			this.artistRepository.save(artistData);
			
		}
		else {
			throw new NotFoundException();
		}
	}
}
