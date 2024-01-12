package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;

@Controller
public class PlaylistController 
{
	
	@Autowired
	PlaylistService playlistservice;
	
	
	@Autowired
	SongService songService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		List<Song> songList=songService.fetchAllSongs();
		model.addAttribute("songs",songList);
				return "createPlaylist";
	}
	@PostMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist)
	{
		 playlistservice.addPlaylist(playlist);
		 
		 List<Song>songList=playlist.getSongs();
		 for(Song s:songList) {
			 s.getPlaylists().add(playlist);
			 songService.updateSong(s);
		 }   
		 return "adminhome";
	}
      @GetMapping("/viewPlaylists")
      public String viewPlaylists(Model model) {
    	  List<Playlist>allPlaylist=playlistservice.fetchallPlaylist();
    	  model.addAttribute("allPlaylists",allPlaylist);
    	  return "displayPlaylists";
      }
}
