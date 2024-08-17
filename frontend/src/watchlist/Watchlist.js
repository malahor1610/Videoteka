import { useState, useEffect } from 'react';
import '../App.css';
import SearchResultDetailsDuration from '../search/result/SearchResultDetailsDuration';
import { Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';

export default function Watchlist({ type }) {
  const [shows, setShows] = useState([]);

  const searchParams = new URLSearchParams({
    sort: "position"
  }).toString();
  useEffect(() => {
    fetch("/shows?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, []);

  return (
    <div className='mt-5 pt-5'>
      {shows.map(show =>
        <Container key={show.id} className='mb-2' style={{'max-width': '500px'}}>
          <Card color="dark" inverse>
            <Row>
              <Col xs='3'>
                <img className='d-block img-fluid img-thumbnail' src={show.poster} />
              </Col>
              <Col>
                <CardBody>
                  <CardTitle tag="h5">
                    {show.title} ({show.originalTitle}) ({show.releaseDate})
                  </CardTitle>
                  <CardSubtitle>
                    <SearchResultDetailsDuration duration={show.duration} />
                  </CardSubtitle>
                </CardBody>
              </Col>
            </Row>
          </Card>
        </Container>
      )}
    </div>
  );
}
