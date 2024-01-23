import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient } from '@angular/common/http';
import { catchError, delay, first, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses';


  constructor(
    private httpClient: HttpClient,
    public dialog: MatDialog
  ) {}

  list() {
    return this.httpClient.get<Course[]>(this.API)
      .pipe(
        catchError(error => {
          this.onError('Error ao carregar cursos');
          return of([])
        }
        )
      )
  }

  loadById(id: string) {
    return  this.httpClient.get<Course>(`${this.API}/${id}`);
  }

  save(record: Partial<Course>) {
    return this.httpClient.post<Course>(this.API, record);
  }

  onError(errorMessage: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMessage
    });
  }
}
