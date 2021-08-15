package com.borzg.towatchlist.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.borzg.domain.model.Movie

class FakeMoviePreviewProvider: PreviewParameterProvider<Movie> {
    override val values: Sequence<Movie>
        get() = sequenceOf(fakeMovie)

    override val count: Int
        get() = 1
}

val fakeMovie: Movie
    get() = Movie(
        id = 671,
        backdrop = null,
        backdropPath = "/4GlSMUpzSd3cliYGFJVziSDX53S.jpg",
        budget = 1248489L,
        imdbId = "tt0241527",
        originalLanguage = "en",
        originalTitle = "Harry Potter and the Philosopher's Stone",
        title = "Гарри Поттер и Философсикй Камень",
        overview = "Harry Potter has lived under the stairs at his aunt and uncle's house his whole life. But on his 11th birthday, he learns he's a powerful wizard -- with a place waiting for him at the Hogwarts School of Witchcraft and Wizardry. As he learns to harness his newfound powers with the help of the school's kindly headmaster, Harry uncovers the truth about his parents' deaths -- and about the villain who's to blame.",
        popularity = 202.505f,
        posterPath = "/eVPs2Y0LyvTLZn6AP5Z6O2rtiGB.jpg",
        releaseDate = "2001-11-16",
        revenue = 976475550L,
        runtime = 152,
        voteAverage = 7.9f,
        voteCount = 20781
    )