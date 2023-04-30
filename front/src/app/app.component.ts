import {Component} from '@angular/core';
import {User} from './user';
import {HttpClient} from "@angular/common/http";




@Component({
  selector: 'app-root',
  template: `

      <div class="container">
          <h1>Age Finder</h1>
          <label>
              Name:
              <input id="name" #name class="form-control"/>
          </label>
          <button (click)="search(name.value)" class="btn btn-primary">Search</button>
          <div *ngIf="user" class="mt-3">
              <p>Имя: {{user.name}}</p>
              <p>Возраст: {{user.age}}</p>
          </div>
          <p *ngIf="error" class="mt-3">{{ error }}</p>
          <h2 class="mt-5">Statistics</h2>
          <table class="table mt-3">
              <thead>
              <tr>
                  <th>Name</th>
                  <th>Requests count</th>
              </tr>
              </thead>
              <tbody>
              <tr *ngFor="let stat of stats">
                  <td>{{ stat['Name'] }}</td>
                  <td>{{ stat['Request count'] }}</td>
              </tr>
              </tbody>
          </table>
          <p class="mt-3">Max age: {{ maxAge }}</p>
      </div>
  `
})
export class AppComponent {

  user: User | undefined;
  error: string = '';
  stats: any[] = [];
  maxAge: number | null = null;

  constructor(private http: HttpClient) {
  }


  search(name: string) {
    console.log(name);
    this.http.get(`http://localhost:8080/api/names/name?name=${name}`)
      .subscribe((data: any) => {
          this.user = new User(name, data.Age);
          this.update();
        },
        (error: any) => {

          this.error = error.message;
        });
  }

  update() {
    this.http.get<any[]>('http://localhost:8080/api/names/stats').subscribe(
      (stats) => {
        this.stats = stats;
      },
      (error) => {
        console.error(error);
      });
    this.http.get<any>('http://localhost:8080/api/names/stats/max-age').subscribe(
      (result) => {
        this.maxAge = result['Age'];
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
